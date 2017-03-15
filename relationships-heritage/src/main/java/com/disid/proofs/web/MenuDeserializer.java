package com.disid.proofs.web;
import com.disid.proofs.domain.Menu;
import com.disid.proofs.service.api.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonObjectDeserializer;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.convert.ConversionService;
import org.springframework.roo.addon.web.mvc.controller.annotations.config.RooDeserializer;

/**
 * = MenuDeserializer
 *
 * TODO Auto-generated class documentation
 *
 */
@RooDeserializer(entity = Menu.class)
public class MenuDeserializer extends JsonObjectDeserializer<Menu> {

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private MenuService menuService;

    /**
     * TODO Auto-generated attribute documentation
     *
     */
    private ConversionService conversionService;

    /**
     * TODO Auto-generated constructor documentation
     *
     * @param menuService
     * @param conversionService
     */
    @Autowired
    public MenuDeserializer(@Lazy MenuService menuService, ConversionService conversionService) {
        this.menuService = menuService;
        this.conversionService = conversionService;
    }
}
