#!/bin/bash
#
# %1$s      Shell script for starting and stopping %1$s
#
# chkconfig: - 95 5
#
### BEGIN INIT INFO
# Provides: %1$s
# Required-Start: $network $syslog
# Required-Stop: $network $syslog
# Default-Start:
# Default-Stop:
# Description: Starts and stops %1$s
# Short-Description: Starts and stops %1$s
### END INIT INFO

NAME=%1$s
JAR="%2$s"
DIR=`dirname "$JAR"`

PIDFILE="$DIR/$NAME.pid"
LOG="$DIR/$NAME.out"
CNF="$DIR/$NAME.cnf"

USER=$(whoami)

# Source the settings
if [ -r "/etc/default/$NAME" ]; then
    source "/etc/default/$NAME"
fi
if [ -r "$CNF" ]; then
    source $CNF
fi
isRunning()
{
if [ -f $PIDFILE ]; then
    PID=`cat $PIDFILE`
    if ps -p $PID > /dev/null; then
        return 1
    else
        return 0
    fi
    rm $PIDFILE
else
    return 1
fi
}

# Function to backup (move) current $JAR to $JAR.bak
backupApp()
{
    if [ -f "$JAR" ]; then
        echo "Creating backup `basename $JAR`.bak"
        mv -f $JAR $JAR.bak
    else
        echo "Nothing to backup, proceed anyway."
    fi
}

# Function to download a new version of the service
fetchApp()
{
    if [ ! -f "$JAR" ]; then
        $DEPLOYCMD
        if [ ! -f "$JAR" ]; then
            echo "DEPLOYCMD failed or did not finish, no file $JAR exists."
            echo "To make $NAME work again, either redeploy using the correct location for new version, "
            echo "or copy a backed up version to $JAR, and then start the service."
            exit 0
        fi
    else
        echo "File $JAR exists, will not overwrite file, remove first."
        exit 0
    fi
}

# Function for starting the service
startConsole()
{
    SAVEPWD=$PWD
    cd $DIR
    STARTCMD="$JAVA_HOME/bin/java $JAVA_OPTS -jar $JAR $JAVA_ARGS"
    if [ "$JAVA_USER" != "$USER" ]; then
        echo $(su $JAVA_USER -c "$STARTCMD >> $LOG 2>&1 & echo \$!") > $PIDFILE
    else
      $STARTCMD >> $LOG 2>&1 &
      echo $! > $PIDFILE
    fi

    cd $SAVEPWD

}

# Function for stopping the service
stopConsole()
{

if [ -f $PIDFILE ]; then
    PID=`cat $PIDFILE`
    if ps -p $PID > /dev/null; then
        kill $PID
    else
    	echo "No process to kill, removing PID."
    fi
    rm $PIDFILE
fi
}

case "$1" in
start)
if [[ -f "${PIDFILE}" ]] ; then
    PID=`cat $PIDFILE`
    if ps -p $PID > /dev/null; then
    	echo "$NAME is already started."
    else
        # The process does not exist, we need to remove the pid file:
        echo "PID file is still present but the process is not running, removing $PIDFILE."
        rm -f "${PIDFILE}"
        echo -n "Starting $NAME"
        startConsole
        echo "."
    fi
else
    echo -n "Starting $NAME"
    startConsole
    echo "."
fi
;;
stop)
if [ -f $PIDFILE ]; then
    echo -n "Stopping $NAME"
    stopConsole
    echo "."
else
    echo "$NAME is already stopped."
fi
;;
restart|force-reload)
echo -n "Restarting $NAME"
stopConsole
sleep 3
startConsole
echo "."
;;
redeploy)
if [ -z "$DEPLOYCMD" ]; then
    echo "Redeployment must be configured by setting DEPLOYCMD in $NAME.cnf"
else
    if [[ -f "${PIDFILE}" ]] ; then
        PID=`cat $PIDFILE`
        if ps -p $PID > /dev/null; then
           echo "Stopping $NAME "
           stopConsole
           sleep 3
        fi
    fi

    backupApp
    echo -n "Fetching new version of $NAME"
    echo "."
    fetchApp
    echo -n "Starting new version of $NAME"
    startConsole
    echo "."

fi
;;
log)
less +G $LOG
;;
dumpstack)
if [[ -f "${PIDFILE}" ]] ; then
    PID=`cat $PIDFILE`
    if ps -p $PID > /dev/null; then
       kill -3 $PID
       less +G $LOG
    else
        echo Process $PID is not running.
    fi
else
  echo $NAME is not running.
fi
;;
lsof)
if [[ -f "${PIDFILE}" ]] ; then
    PID=`cat $PIDFILE`
    if ps -p $PID > /dev/null; then
       lsof -p $PID |less
    else
        echo Process $PID is not running.
    fi
else
  echo $NAME is not running.
fi
;;
*)
echo "Usage: $NAME {start|stop|restart|force-reload|redeploy|log|dumpstack|lsof}" >&2
exit 1
;;
esac

exit 0