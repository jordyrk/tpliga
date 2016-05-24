/*
 * Copyright 2009 Kantega AS
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.tpl;

import java.io.IOException;
import java.util.Date;
import no.kantega.commons.exception.NotAuthorizedException;
import no.kantega.commons.log.Log;
import no.kantega.publishing.api.services.DefaultContentCreator;
import no.kantega.publishing.common.ao.MultimediaAO;
import no.kantega.publishing.common.data.Association;
import no.kantega.publishing.common.data.AssociationCategory;
import no.kantega.publishing.common.data.Content;
import no.kantega.publishing.common.data.Multimedia;
import no.kantega.publishing.common.data.attributes.EmailAttribute;
import no.kantega.publishing.common.data.attributes.HtmltextAttribute;
import no.kantega.publishing.common.data.attributes.ImageAttribute;
import no.kantega.publishing.common.data.attributes.MediafolderAttribute;
import no.kantega.publishing.common.data.attributes.TextAttribute;
import no.kantega.publishing.common.data.enums.AttributeDataType;
import no.kantega.publishing.common.data.enums.ContentStatus;
import no.kantega.publishing.common.data.enums.ContentType;
import no.kantega.publishing.common.data.enums.MultimediaType;
import no.kantega.publishing.common.service.ContentManagementService;
import org.springframework.core.io.ClassPathResource;

/**
 * Note that contentTemplateIds used here must match those set in
 * aksess-templateconfig.xml
 *
 * @author jogri
 */
public class DefaultContentCreatorStandard implements DefaultContentCreator {

    private final String SOURCE = getClass().getCanonicalName();

    private Multimedia multimediaFolder;

    public void createDefaultContent(ContentManagementService contentManagementService, Content contentRoot) throws NotAuthorizedException {
        // Lead paragraph
        TextAttribute textAttribute = new TextAttribute();
        textAttribute.setName("lead paragraph");
        textAttribute.setValue("Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.");
        contentRoot.setDescription(textAttribute.getValue());
        contentRoot.addAttribute(textAttribute, AttributeDataType.CONTENT_DATA);

        // Create logo folder
        Multimedia multimediaLogoFolder = new Multimedia();
        multimediaLogoFolder.setName("Logoer");
        multimediaLogoFolder.setType(MultimediaType.FOLDER);
        MultimediaAO.setMultimedia(multimediaLogoFolder);

        // Store logo image in a multimedia folder
        Multimedia multimediaLogo = new Multimedia();
        multimediaLogo.setFilename("my_logo.png");
        multimediaLogo.setWidth(144);
        multimediaLogo.setHeight(53);
        multimediaLogo.setName("Logo");
        multimediaLogo.setParentId(multimediaLogoFolder.getId());
        ClassPathResource classPathResource = new ClassPathResource(multimediaLogo.getFilename());
        try {
            byte[] data = new byte[(int) classPathResource.getFile().length()];
            classPathResource.getInputStream().read(data);
            multimediaLogo.setData(data);
        } catch (IOException ex) {
            Log.error(SOURCE, ex, null, null);
        }
        ImageAttribute imageAttribute = new ImageAttribute();
        imageAttribute.setValue(MultimediaAO.setMultimedia(multimediaLogo) + "");
        imageAttribute.setName("logo");
        contentRoot.addAttribute(imageAttribute, AttributeDataType.CONTENT_DATA);

        // Content
        HtmltextAttribute htmltextAttribute  = new HtmltextAttribute();
        htmltextAttribute.setName("content");
        htmltextAttribute.setValue("Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.<br><br>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut <a href=\"?thisId=1\">enim ad minim</a> veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
        contentRoot.addAttribute(htmltextAttribute, AttributeDataType.CONTENT_DATA);

        // E-mail address
        EmailAttribute emailAttribute = new EmailAttribute();
        emailAttribute.setName("emailAddress");
        emailAttribute.setValue("contact@mycompany.com");
        contentRoot.addAttribute(emailAttribute, AttributeDataType.CONTENT_DATA);

        contentManagementService.checkInContent(contentRoot, ContentStatus.PUBLISHED);

        // Subpages
        createLink(contentManagementService, contentRoot);
        createNewsArchive(contentManagementService, contentRoot);
        createArticle(contentManagementService, contentRoot);
        createSearch(contentManagementService, contentRoot);
        createAlphabeticalList(contentManagementService, contentRoot);
        createPhotoAlbum(contentManagementService, contentRoot);
    }

    public void createLink(ContentManagementService contentManagementService, Content contentRoot) throws NotAuthorizedException {
        Content content = new Content();
        content.setGroupId(1);
        content.setCreateDate(new Date());
        content.setContentTemplateId(9);
        content.setType(ContentType.LINK);
        content.setTitle("Hovedside");
        content.setLocation("/content.ap?thisId=1");
        content.setPublisher(contentManagementService.getSecuritySession().getUser().getName());

        Association association = new Association();
        association.setParentAssociationId(contentRoot.getId());
        association.setPriority(1);
        association.setPath("/1/");
        association.setDepth(1);
        association.setCurrent(true);
        association.setCategory(new AssociationCategory(1));
        content.addAssociation(association);

        content.setIsModified(true);
        content.setIsCheckedOut(true);

        contentManagementService.checkInContent(content, ContentStatus.PUBLISHED);
    }

    public Content createNewsArchive(ContentManagementService contentManagementService, Content contentRoot) throws NotAuthorizedException {
        Content content = new Content();
        content.setGroupId(1);
        content.setCreateDate(new Date());
        content.setContentTemplateId(3);
        content.setDisplayTemplateId(3);
        content.setTitle("Nyhetsarkiv");
        content.setPublisher(contentManagementService.getSecuritySession().getUser().getName());
        content.setLastModified(new Date());

        TextAttribute textAttribute = new TextAttribute();
        textAttribute.setName("lead paragraph");
        textAttribute.setValue("Samleside for publisering av nyheter.");
        content.setDescription(textAttribute.getValue());
        content.addAttribute(textAttribute, AttributeDataType.CONTENT_DATA);

        Association association = new Association();
        association.setParentAssociationId(contentRoot.getId());
        association.setPriority(2);
        association.setPath("/1/");
        association.setDepth(1);
        association.setCurrent(true);
        association.setCategory(new AssociationCategory(1));
        content.addAssociation(association);

        content.setIsModified(true);
        content.setIsCheckedOut(true);

        return contentManagementService.checkInContent(content, ContentStatus.PUBLISHED);
    }

    public Content createArticle(ContentManagementService contentManagementService, Content contentRoot) throws NotAuthorizedException {
        Content content = new Content();
        content.setGroupId(1);
        content.setCreateDate(new Date());
        content.setContentTemplateId(2);
        content.setDisplayTemplateId(2);
        content.setTitle("Standard underside");
        content.setPublisher(contentManagementService.getSecuritySession().getUser().getName());
        content.setLastModified(new Date());

        // Lead paragraph
        TextAttribute textAttribute = new TextAttribute();
        textAttribute.setName("lead paragraph");
        textAttribute.setValue("Ordinær side med tekst og bilder.");
        content.setDescription(textAttribute.getValue());
        content.addAttribute(textAttribute, AttributeDataType.CONTENT_DATA);

        // Create image folder
        multimediaFolder = new Multimedia();
        multimediaFolder.setName("Bilder");
        multimediaFolder.setType(MultimediaType.FOLDER);
        MultimediaAO.setMultimedia(multimediaFolder);

        // Store image in multimedia folder
        Multimedia image = new Multimedia();
        image.setFilename("costa_rican_frog.png");
        image.setName("Costa Rican Frog");
        image.setParentId(multimediaFolder.getId());
        ClassPathResource classPathResource = new ClassPathResource(image.getFilename());
        try {
            byte[] data = new byte[(int) classPathResource.getFile().length()];
            classPathResource.getInputStream().read(data);
            image.setData(data);
        } catch (IOException ex) {
            Log.error(SOURCE, ex, null, null);
        }
        ImageAttribute imageAttribute = new ImageAttribute();
        imageAttribute.setValue(MultimediaAO.setMultimedia(image) + "");
        imageAttribute.setName("image");
        content.setImage(imageAttribute.getValue());
        content.addAttribute(imageAttribute, AttributeDataType.CONTENT_DATA);

        // Image text
        TextAttribute caption = new TextAttribute();
        caption.setName("caption");
        caption.setValue("Lorem ipsum dolor sit amet, consectetur adipisicing elit.");
        content.setDescription(caption.getValue());
        content.addAttribute(caption, AttributeDataType.CONTENT_DATA);

        // Article text
        HtmltextAttribute htmltextAttribute  = new HtmltextAttribute();
        htmltextAttribute.setName("running text");
        htmltextAttribute.setValue("Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.<br><br>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut <a href=\"?thisId=1\">enim ad minim</a> veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
        content.addAttribute(htmltextAttribute, AttributeDataType.CONTENT_DATA);

        Association association = new Association();
        association.setParentAssociationId(contentRoot.getId());
        association.setPriority(3);
        association.setPath("/1/");
        association.setDepth(1);
        association.setCurrent(true);
        association.setCategory(new AssociationCategory(1));
        content.addAssociation(association);

        content.setIsModified(true);
        content.setIsCheckedOut(true);

        Content contentArticle = contentManagementService.checkInContent(content, ContentStatus.PUBLISHED);


        // Create sub article

        Content subpage = new Content();
        subpage.setGroupId(1);
        subpage.setCreateDate(new Date());
        subpage.setContentTemplateId(2);
        subpage.setDisplayTemplateId(2);
        subpage.setTitle("Underside");
        subpage.setPublisher(contentManagementService.getSecuritySession().getUser().getName());
        subpage.setLastModified(new Date());

        // Lead paragraph
        TextAttribute leadParagraph = new TextAttribute();
        leadParagraph.setName("lead paragraph");
        leadParagraph.setValue("Ordinær side med tekst og bilder.");
        subpage.setDescription(leadParagraph.getValue());
        subpage.addAttribute(leadParagraph, AttributeDataType.CONTENT_DATA);

        // Store image in multimedia folder
        image = new Multimedia();
        image.setFilename("boston_city_flow.png");
        image.setName("Boston City Flow");
        image.setParentId(multimediaFolder.getId());
        classPathResource = new ClassPathResource(image.getFilename());
        try {
            byte[] data = new byte[(int) classPathResource.getFile().length()];
            classPathResource.getInputStream().read(data);
            image.setData(data);
        } catch (IOException ex) {
            Log.error(SOURCE, ex, null, null);
        }
        imageAttribute = new ImageAttribute();
        imageAttribute.setValue(MultimediaAO.setMultimedia(image) + "");
        imageAttribute.setName("image");
        subpage.setImage(imageAttribute.getValue());
        subpage.addAttribute(imageAttribute, AttributeDataType.CONTENT_DATA);

        // Image text
        caption = new TextAttribute();
        caption.setName("caption");
        caption.setValue("Lorem ipsum dolor sit amet, consectetur adipisicing elit.");
        subpage.setDescription(caption.getValue());
        subpage.addAttribute(caption, AttributeDataType.CONTENT_DATA);

        // Article text
        htmltextAttribute  = new HtmltextAttribute();
        htmltextAttribute.setName("running text");
        htmltextAttribute.setValue("Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.<br><br>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut <a href=\"?thisId=1\">enim ad minim</a> veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
        subpage.addAttribute(htmltextAttribute, AttributeDataType.CONTENT_DATA);

        association = new Association();
        association.setParentAssociationId(contentArticle.getId());
        association.setPriority(1);
        association.setPath("/1/" + association.getParentAssociationId() + "/");
        association.setDepth(2);
        association.setCurrent(true);
        association.setCategory(new AssociationCategory(1));
        subpage.addAssociation(association);

        subpage.setIsModified(true);
        subpage.setIsCheckedOut(true);

        contentManagementService.checkInContent(subpage, ContentStatus.PUBLISHED);



        return contentArticle;
    }

    public Content createSearch(ContentManagementService contentManagementService, Content contentRoot) throws NotAuthorizedException {
        Content content = new Content();
        content.setGroupId(1);
        content.setCreateDate(new Date());
        content.setContentTemplateId(6);
        content.setDisplayTemplateId(6);
        content.setTitle("Søk");
        content.setAlias("/search/");
        content.setPublisher(contentManagementService.getSecuritySession().getUser().getName());
        content.setLastModified(new Date());

        TextAttribute textAttribute = new TextAttribute();
        textAttribute.setName("lead paragraph");
        textAttribute.setValue("Side for søk og visning av søkeresultat.");
        content.setDescription(textAttribute.getValue());
        content.addAttribute(textAttribute, AttributeDataType.CONTENT_DATA);

        Association association = new Association();
        association.setParentAssociationId(contentRoot.getId());
        association.setPriority(4);
        association.setPath("/1/");
        association.setDepth(1);
        association.setCurrent(true);
        association.setCategory(new AssociationCategory(1));
        content.addAssociation(association);

        content.setIsModified(true);
        content.setIsCheckedOut(true);

        return contentManagementService.checkInContent(content, ContentStatus.PUBLISHED);
    }

    public Content createAlphabeticalList(ContentManagementService contentManagementService, Content contentRoot) throws NotAuthorizedException {
        Content content = new Content();
        content.setGroupId(1);
        content.setCreateDate(new Date());
        content.setContentTemplateId(13);
        content.setDisplayTemplateId(13);
        content.setTitle("Alfabetisk liste");
        content.setPublisher(contentManagementService.getSecuritySession().getUser().getName());
        content.setLastModified(new Date());

        TextAttribute textAttribute = new TextAttribute();
        textAttribute.setName("lead paragraph");
        textAttribute.setValue("Alfabetisk liste over undersider. Undersidene grupperes etter forbokstav og i toppen av siden er det snarveilenker til hver gruppe med undersider.");
        content.setDescription(textAttribute.getValue());
        content.addAttribute(textAttribute, AttributeDataType.CONTENT_DATA);

        Association association = new Association();
        association.setParentAssociationId(contentRoot.getId());
        association.setPriority(5);
        association.setPath("/1/");
        association.setDepth(1);
        association.setCurrent(true);
        association.setCategory(new AssociationCategory(1));
        content.addAssociation(association);

        content.setIsModified(true);
        content.setIsCheckedOut(true);

        return contentManagementService.checkInContent(content, ContentStatus.PUBLISHED);
    }

    public Content createList(ContentManagementService contentManagementService, Content contentRoot) throws NotAuthorizedException {
        Content content = new Content();
        content.setGroupId(1);
        content.setCreateDate(new Date());
        content.setContentTemplateId(12);
        content.setDisplayTemplateId(12);
        content.setTitle("Liste");
        content.setPublisher(contentManagementService.getSecuritySession().getUser().getName());
        content.setLastModified(new Date());

        TextAttribute textAttribute = new TextAttribute();
        textAttribute.setName("lead paragraph");
        textAttribute.setValue("Mal som lister ut sine undersider i midtspalten. Du kan velge å vise tittelen og ingressen fra hver underside eller bare titlen.");
        content.setDescription(textAttribute.getValue());
        content.addAttribute(textAttribute, AttributeDataType.CONTENT_DATA);

        Association association = new Association();
        association.setParentAssociationId(contentRoot.getId());
        association.setPriority(6);
        association.setPath("/1/");
        association.setDepth(1);
        association.setCurrent(true);
        association.setCategory(new AssociationCategory(1));
        content.addAssociation(association);

        content.setIsModified(true);
        content.setIsCheckedOut(true);

        return contentManagementService.checkInContent(content, ContentStatus.PUBLISHED);
    }

    public Content createPhotoAlbum(ContentManagementService contentManagementService, Content contentRoot) throws NotAuthorizedException {
        Content content = new Content();
        content.setGroupId(1);
        content.setCreateDate(new Date());
        content.setContentTemplateId(8);
        content.setDisplayTemplateId(8);
        content.setTitle("Fotoalbum");
        content.setPublisher(contentManagementService.getSecuritySession().getUser().getName());
        content.setLastModified(new Date());

        TextAttribute textAttribute = new TextAttribute();
        textAttribute.setName("lead paragraph");
        textAttribute.setValue("Mal hvor du kan velge en folder fra multimediearkivet som skal vises som fotogalleri.");
        content.setDescription(textAttribute.getValue());
        content.addAttribute(textAttribute, AttributeDataType.CONTENT_DATA);

        MediafolderAttribute mediafolderAttribute = new MediafolderAttribute();
        mediafolderAttribute.setName("mediafolder");
        mediafolderAttribute.setValue(multimediaFolder.getId() + "");
        content.addAttribute(mediafolderAttribute, AttributeDataType.CONTENT_DATA);

        Association association = new Association();
        association.setParentAssociationId(contentRoot.getId());
        association.setPriority(7);
        association.setPath("/1/");
        association.setDepth(1);
        association.setCurrent(true);
        association.setCategory(new AssociationCategory(1));
        content.addAssociation(association);

        content.setIsModified(true);
        content.setIsCheckedOut(true);

        return contentManagementService.checkInContent(content, ContentStatus.PUBLISHED);
    }

    public String getDescription() {
        return "Creates simple dummy content for some of the standard templates included in this archetype";
    }
}
