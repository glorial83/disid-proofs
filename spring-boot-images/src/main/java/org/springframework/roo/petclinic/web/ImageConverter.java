package org.springframework.roo.petclinic.web;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.roo.petclinic.domain.Image;
import org.springframework.roo.petclinic.service.api.ImageService;
import org.springframework.stereotype.Component;

/**
 * Converter to obtain a valid image from a provided String
 * 
 * @author jcgarcia at http://www.disid.com[DISID Corporation S.L.]
 */
@Component
public class ImageConverter implements Converter<String, Image> {

  @Autowired
  private ImageService imageService;

  @Override
  public Image convert(String id) {
    if (StringUtils.isEmpty(id)) {
      return null;
    }
    return imageService.findOne(Long.parseLong(id));
  }

}
