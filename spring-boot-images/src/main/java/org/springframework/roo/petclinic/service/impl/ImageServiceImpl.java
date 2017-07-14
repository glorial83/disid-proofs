package org.springframework.roo.petclinic.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.layers.service.annotations.RooServiceImpl;
import org.springframework.roo.petclinic.domain.Image;
import org.springframework.roo.petclinic.repository.ImageRepository;
import org.springframework.roo.petclinic.service.api.ImageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * = ImageServiceImpl
 *
 * TODO Auto-generated class documentation
 *
 */
@RooServiceImpl(service = ImageService.class)
@Service
@Transactional(readOnly = true)
public class ImageServiceImpl implements ImageService {

  @Autowired
  private ImageRepository repository;

  /**
   * {@inheritDoc}
   */
  @Override
  @Transactional
  public Image save(Image image) {
    return repository.save(image);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<Image> findAll() {
    return repository.findAll();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Image findOne(Long id) {
    return repository.findOne(id);
  }

}
