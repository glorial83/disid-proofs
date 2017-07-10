package org.springframework.roo.petclinic.web;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.env.Environment;
import org.springframework.roo.petclinic.domain.Image;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * = ImageFileConverter
 * 
 * <p>
 * Converter that will convert the received MultipartFiles to a Image type
 * </p>
 * 
 * <p>
 * This converter will be active only if the <code>${springlets.image.management}</code>
 * property has been registered in the {@link Environment} and if it has been setted with 
 * <code>true</code> value.
 * </p>
 *  
 * @author jcgarcia at http://www.disid.com[DISID Corporation S.L.]
 */
@Component
@ConditionalOnProperty(name = "springlets.image.management", havingValue = "true")
public class ImageFileConverter implements Converter<MultipartFile, Image> {

  /**
   * Transforms the received MultipartFile to a Image file
   */
  @Override
  public Image convert(MultipartFile source) {

    try {
      // First of all, obtain the content type
      String contentType = source.getContentType();

      // If the provided MultipartFile is an image, create a new
      // Image item using the source bytes
      if (contentType.startsWith("image/")) {
        return new Image(source.getBytes());
      }

    } catch (IOException e) {
      e.printStackTrace();
    }
    // If is not an image, this converter
    // could not convert the provided MultipartFile
    return null;

  }



}
