package org.springframework.roo.petclinic.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * = MultipartFileConverter
 * 
 * <p>
 * Converter that will convert the received MultipartFiles to a Byte Array.
 * </p>
 * 
 * <p>
 * This converter will be active only if the <code>${springlets.image.management}</code>
 * property has been registered in the {@link Environment} and if it has been setted with 
 * <code>true</code> value.
 * </p>
 * <p>
 * During the conversion, it apply the following operations to the returned byte array:
 * </p>
 * 
 * * Format: The original image will be formatted to the format specified in the
 *           ${springlets.image.management.format.extension} property. This operation is
 *           disabled by default and should be enabled using the ${springlets.image.management.format}
 *           property with true value..
 *           
 *  * Resize: The original image will be resized to the width specified into the
 *            ${springlets.image.management.resize.width} property and to the height specified into the
 *            ${springlets.image.management.resize.height} property. This operation is disabled by default
 *            and should be enabled using the ${springlets.image.management.resize} property with 
 *            true value.
 *  
 * @author jcgarcia at http://www.disid.com[DISID Corporation S.L.]
 */
@Component
@ConditionalOnProperty(name = "springlets.image.management", havingValue = "true")
public class MultipartFileConverter implements Converter<MultipartFile, byte[]> {

  private static final Logger LOG = LoggerFactory.getLogger(MultipartFileConverter.class);

  /**
   * Property that defines if the image format is enabled or not.
   * 
   * By default, it will be false
   */
  @Value("${springlets.image.management.format:false}")
  private boolean isImageFormatEnabled = false;

  /**
   * Property that defines the new format that should be returned in the image
   * 
   * By default, it will be PNG extension.
   */
  @Value("${springlets.image.management.format.extension:png}")
  private String imageFormat = "png";

  /**
   * Property that defines if the image resize is enabled or not.
   * 
   * By default, it will be false
   */
  @Value("${springlets.image.management.resize:false}")
  private boolean isImageResizeEnabled = false;

  /**
   * Property that defines the with that should be used during resize
   * 
   * By default, it will be 100px
   */
  @Value("${springlets.image.management.resize.width:100}")
  private int resizeWidth = 100;

  /**
   * Property that defines the with that should be used during resize
   * 
   * By default, it will be 100px
   */
  @Value("${springlets.image.management.resize.height:100}")
  private int resizeHeight = 100;

  /**
   * Transforms the received MultipartFile to a Byte Array
   */
  @Override
  public byte[] convert(MultipartFile source) {
    try {
      // First of all, obtain the content type
      String contentType = source.getContentType();

      // The following validations and changes will be applied only
      // for images multipart files and if format image property is enabled 
      // or resize image property is enabled
      if (contentType.startsWith("image/") && (isImageFormatEnabled || isImageResizeEnabled)) {

        // Obtain the image extension
        String imageExtension = contentType.replaceAll("image/", "");

        // Obtain the buffer image
        BufferedImage imageBuffer = ImageIO.read(source.getInputStream());

        // Maybe, some image will not be available
        if (imageBuffer == null) {
          LOG.debug(
              "ERROR: The provided ." + imageExtension + " image '" + source.getOriginalFilename()
                  + "' could not be resized and could not change the format. Image will be returned with the "
                  + "original format and size.");
          return source.getBytes();
        }

        // If the image resize is enabled, resize the image before continue
        if (isImageResizeEnabled) {
          imageBuffer = resizeImage(imageBuffer);
        }

        // If image format is enabled, write the original or resized image 
        // into byteArrayOut using the provided format
        ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
        if (isImageFormatEnabled) {
          ImageIO.write(imageBuffer, imageFormat, byteArrayOut);
        } else {
          // If not, write the original or resized image into the byteArrayOut
          // using the original format.
          ImageIO.write(imageBuffer, imageExtension, byteArrayOut);
        }

        // Finally, return the byteArrayOut that could be original or resized
        // and formatted.
        return byteArrayOut.toByteArray();

      }

      // If is not an image MultiPart file, return the bytes 
      // of the received image
      return source.getBytes();

    } catch (IOException e) {
      throw new RuntimeException(e.getMessage());
    }
  }

  /**
   * Method that resizes the provided image using the properties defined by
   * the developers.
   * 
   * @param originalImage
   * @param type
   * @return
   */
  private BufferedImage resizeImage(BufferedImage originalImage) {
    // Getting the image type
    int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();

    // Creating new BufferedImage with the new resized size
    BufferedImage resizedImage = new BufferedImage(resizeWidth, resizeHeight, type);

    // Creating graphics that will create the resize image
    Graphics2D g = resizedImage.createGraphics();
    g.drawImage(originalImage, 0, 0, resizeWidth, resizeHeight, null);
    g.dispose();

    // Return the resized image
    return resizedImage;
  }

}
