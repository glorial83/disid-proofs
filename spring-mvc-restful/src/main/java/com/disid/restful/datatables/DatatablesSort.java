package com.disid.restful.datatables;

import org.springframework.data.domain.Sort;
import org.springframework.roo.addon.web.mvc.thymeleaf.annotations.RooThymeleafDatatablesSort;

import java.util.List;

@RooThymeleafDatatablesSort
public class DatatablesSort extends Sort {

  private static final long serialVersionUID = 5938901146261470479L;

  public DatatablesSort(List<Order> orders) {
    super(orders);
  }

}
