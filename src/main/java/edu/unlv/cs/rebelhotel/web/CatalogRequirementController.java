package edu.unlv.cs.rebelhotel.web;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import edu.unlv.cs.rebelhotel.domain.CatalogRequirement;
import org.springframework.roo.addon.web.mvc.controller.RooWebScaffold;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@RooWebScaffold(path = "catalogrequirements", formBackingObject = CatalogRequirement.class)
@RequestMapping("/catalogrequirements")
@Controller
public class CatalogRequirementController {
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPERUSER')")
	@RequestMapping(method = RequestMethod.POST)
    public String create(@Valid CatalogRequirement catalogRequirement, BindingResult result, Model model, HttpServletRequest request) {
        if (result.hasErrors()) {
            model.addAttribute("catalogRequirement", catalogRequirement);
            return "catalogrequirements/create";
        }
        catalogRequirement.persist();
        return "redirect:/catalogrequirements/" + encodeUrlPathSegment(catalogRequirement.getId().toString(), request);
    }
    
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPERUSER')")
    @RequestMapping(params = "form", method = RequestMethod.GET)
    public String createForm(Model model) {
        model.addAttribute("catalogRequirement", new CatalogRequirement());
        return "catalogrequirements/create";
    }
    
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPERUSER')")
    @RequestMapping(method = RequestMethod.PUT)
    public String update(@Valid CatalogRequirement catalogRequirement, BindingResult result, Model model, HttpServletRequest request) {
        if (result.hasErrors()) {
            model.addAttribute("catalogRequirement", catalogRequirement);
            return "catalogrequirements/update";
        }
        catalogRequirement.merge();
        return "redirect:/catalogrequirements/" + encodeUrlPathSegment(catalogRequirement.getId().toString(), request);
    }
    
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPERUSER')")
    @RequestMapping(value = "/{id}", params = "form", method = RequestMethod.GET)
    public String updateForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("catalogRequirement", CatalogRequirement.findCatalogRequirement(id));
        return "catalogrequirements/update";
    }
    
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_SUPERUSER')")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model model) {
        CatalogRequirement.findCatalogRequirement(id).remove();
        model.addAttribute("page", (page == null) ? "1" : page.toString());
        model.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/catalogrequirements?page=" + ((page == null) ? "1" : page.toString()) + "&size=" + ((size == null) ? "10" : size.toString());
    }
}
