package org.springframework.roo.multiselect.web;

import org.springframework.roo.addon.web.mvc.thymeleaf.annotations.RooLinkFactory;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

import java.io.IOException;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;

/**
 * = OwnersCollectionThymeleafLinkFactory
 *
 * TODO Auto-generated class documentation
 *
 */
@RooLinkFactory(controller = OwnersCollectionThymeleafController.class)
public class OwnersCollectionThymeleafLinkFactory {
  /**
   * TODO Auto-generated attribute documentation
   */
  private static final String LIST = "list";

  /**
   * TODO Auto-generated attribute documentation
   */
  private static final String DATATABLES = "datatables";

  /**
   * TODO Auto-generated attribute documentation
   */
  private static final String CREATE = "create";

  /**
   * TODO Auto-generated attribute documentation
   */
  private static final String CREATE_FORM = "createForm";

  /**
   * TODO Auto-generated attribute documentation
   */
  private static final String DELETE_BATCH = "deleteBatch";

  /**
   * TODO Auto-generated attribute documentation
   */
  private static final String EXPORT_CSV = "exportCsv";

  /**
   * TODO Auto-generated attribute documentation
   */
  private static final String EXPORT_PDF = "exportPdf";

  /**
   * TODO Auto-generated attribute documentation
   */
  private static final String EXPORT_XLS = "exportXls";

  /**
   * TODO Auto-generated method documentation
   * 
   * @param methodName
   * @param parameters
   * @param pathVariables
   * @return UriComponents
   * @throws JRException
   * @throws IOException
   * @throws ClassNotFoundException
   */
  public UriComponents toUri(String methodName, Object[] parameters,
      Map<String, Object> pathVariables) {
    try {
      if (methodName.equals(LIST)) {
        return MvcUriComponentsBuilder
            .fromMethodCall(MvcUriComponentsBuilder.on(getControllerClass()).list(null)).build();
      }
      if (methodName.equals(DATATABLES)) {
        return MvcUriComponentsBuilder
            .fromMethodCall(
                MvcUriComponentsBuilder.on(getControllerClass()).datatables(null, null, null))
            .replaceQuery(null).build();
      }
      if (methodName.equals(CREATE)) {
        return MvcUriComponentsBuilder.fromMethodCall(
            MvcUriComponentsBuilder.on(getControllerClass()).create(null, null, null)).build();
      }
      if (methodName.equals(CREATE_FORM)) {
        return MvcUriComponentsBuilder
            .fromMethodCall(MvcUriComponentsBuilder.on(getControllerClass()).createForm(null))
            .build();
      }
      if (methodName.equals(DELETE_BATCH)) {
        return MvcUriComponentsBuilder
            .fromMethodCall(MvcUriComponentsBuilder.on(getControllerClass()).deleteBatch(null))
            .build();
      }
      if (methodName.equals(EXPORT_CSV)) {
        return MvcUriComponentsBuilder
            .fromMethodCall(
                MvcUriComponentsBuilder.on(getControllerClass()).exportCsv(null, null, null, null))
            .build();
      }
      if (methodName.equals(EXPORT_PDF)) {
        return MvcUriComponentsBuilder
            .fromMethodCall(
                MvcUriComponentsBuilder.on(getControllerClass()).exportPdf(null, null, null, null))
            .build();
      }
      if (methodName.equals(EXPORT_XLS)) {
        return MvcUriComponentsBuilder
            .fromMethodCall(
                MvcUriComponentsBuilder.on(getControllerClass()).exportXls(null, null, null, null))
            .build();
      }
    } catch (Exception e) {
      return null;
    }

    throw new IllegalArgumentException("Invalid method name: " + methodName);
  }


}
