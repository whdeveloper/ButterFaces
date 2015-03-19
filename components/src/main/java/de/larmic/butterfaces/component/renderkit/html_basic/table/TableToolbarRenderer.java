package de.larmic.butterfaces.component.renderkit.html_basic.table;

import de.larmic.butterfaces.component.html.table.HtmlColumn;
import de.larmic.butterfaces.component.html.table.HtmlTable;
import de.larmic.butterfaces.component.html.table.HtmlTableToolbar;
import de.larmic.butterfaces.component.partrenderer.RenderUtils;
import de.larmic.butterfaces.component.partrenderer.StringUtils;
import de.larmic.butterfaces.component.renderkit.html_basic.HtmlBasicRenderer;
import de.larmic.butterfaces.resolver.AjaxCall;
import de.larmic.butterfaces.resolver.UIComponentResolver;

import javax.faces.component.UIComponent;
import javax.faces.component.UINamingContainer;
import javax.faces.component.behavior.AjaxBehavior;
import javax.faces.component.behavior.ClientBehavior;
import javax.faces.component.behavior.ClientBehaviorContext;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by larmic on 10.09.14.
 */
@FacesRenderer(componentFamily = HtmlTableToolbar.COMPONENT_FAMILY, rendererType = HtmlTableToolbar.RENDERER_TYPE)
public class TableToolbarRenderer extends HtmlBasicRenderer {

    private HtmlTable cachedTableComponent;

    @Override
    public void encodeBegin(final FacesContext context,
                            final UIComponent component) throws IOException {
        rendererParamsNotNull(context, component);

        if (!shouldEncode(component)) {
            return;
        }

        super.encodeBegin(context, component);

        final HtmlTableToolbar tableHeader = (HtmlTableToolbar) component;
        final ResponseWriter responseWriter = context.getResponseWriter();
        this.cachedTableComponent = new UIComponentResolver().findComponent(tableHeader.getTableId(), HtmlTable.class);

        responseWriter.startElement("div", tableHeader);
        this.writeIdAttribute(context, responseWriter, tableHeader);
        responseWriter.writeAttribute("class", "butter-table-toolbar", null);
    }

    @Override
    public void encodeChildren(final FacesContext context,
                               final UIComponent component) throws IOException {
        if (component.getChildCount() > 0) {
            final ResponseWriter responseWriter = context.getResponseWriter();

            responseWriter.startElement("div", component);
            responseWriter.writeAttribute("class", "butter-table-toolbar-custom pull-left", null);
            super.encodeChildren(context, component);
            responseWriter.endElement("div");
        }
    }

    @Override
    public void encodeEnd(final FacesContext context,
                          final UIComponent component) throws IOException {
        super.encodeEnd(context, component);

        final HtmlTableToolbar tableHeader = (HtmlTableToolbar) component;
        final ResponseWriter responseWriter = context.getResponseWriter();

        responseWriter.startElement("div", tableHeader); // start right toolbar
        responseWriter.startElement("div", tableHeader); // start button group
        responseWriter.writeAttribute("class", "btn-group pull-right table-toolbar-default", null);

        this.renderFacet(context, component, "default-options-left");
        this.renderTableToolbarRefreshButton(responseWriter, tableHeader);
        this.renderFacet(context, component, "default-options-center");
        this.renderTableToolbarToggleColumnButton(context, responseWriter, tableHeader);
        this.renderFacet(context, component, "default-options-right");

        responseWriter.endElement("div"); // end button group
        responseWriter.endElement("div"); // end right toolbar

        responseWriter.endElement("div");

        RenderUtils.renderJQueryPluginCall(component.getClientId(), "fixBootstrapDropDown()", responseWriter, component);
    }

    private void renderFacet(final FacesContext context, final UIComponent component, final String facetName) throws IOException {
        final UIComponent leftFacet = this.getFacet(component, facetName);

        if (leftFacet != null) {
            leftFacet.encodeAll(context);
        }
    }

    @Override
    public void decode(FacesContext context, UIComponent component) {
        final HtmlTableToolbar htmlTableHeader = (HtmlTableToolbar) component;
        final Map<String, List<ClientBehavior>> behaviors = htmlTableHeader.getClientBehaviors();

        if (behaviors.isEmpty()) {
            return;
        }

        final ExternalContext external = context.getExternalContext();
        final Map<String, String> params = external.getRequestParameterMap();
        final String behaviorEvent = params.get("javax.faces.behavior.event");

        if (behaviorEvent != null) {
            if (behaviorEvent.startsWith("toggle")) {
                final String[] split = behaviorEvent.split("_");
                final String event = split[0];
                final int eventNumber = Integer.valueOf(split[1]);
                if ("toggle".equals(event) && this.cachedTableComponent.getTableColumnDisplayModel() != null) {
                    final HtmlColumn toggledColumn = this.cachedTableComponent.getCachedColumns().get(eventNumber);
                    if (this.isHideColumn(this.cachedTableComponent, toggledColumn)) {
                        this.cachedTableComponent.getTableColumnDisplayModel().showColumn(toggledColumn.getId());
                    } else {
                        this.cachedTableComponent.getTableColumnDisplayModel().hideColumn(toggledColumn.getId());
                    }
                }
            } else if (behaviorEvent.equals("refresh")) {
                if (htmlTableHeader.getTableToolbarRefreshListener() != null) {
                    htmlTableHeader.getTableToolbarRefreshListener().onPreRefresh();
                }
            }
        }
    }

    @Override
    public boolean getRendersChildren() {
        return true;
    }

    private void renderTableToolbarToggleColumnButton(final FacesContext context,
                                                      final ResponseWriter writer,
                                                      final HtmlTableToolbar tableHeader) throws IOException {
        if (tableHeader.isShowToggleColumnButton()) {
            writer.startElement("div", tableHeader);
            writer.writeAttribute("class", "btn-group", null);

            // show and hide option toggle
            writer.startElement("a", tableHeader);
            writer.writeAttribute("class", "btn btn-default dropdown-toggle", null);
            writer.writeAttribute("data-toggle", "dropdown", null);
            writer.writeAttribute("title", "Column options", null);
            writer.writeAttribute("role", "button", null);
            writer.startElement("i", tableHeader);
            writer.writeAttribute("class", "glyphicon glyphicon-th", null);
            writer.endElement("i");
            writer.startElement("span", tableHeader);
            writer.writeAttribute("class", "caret", null);
            writer.endElement("span");
            writer.endElement("a");

            // show and hide option content
            writer.startElement("ul", tableHeader);
            writer.writeAttribute("class", "dropdown-menu dropdown-menu-form butter-table-toolbar-columns", null);
            writer.writeAttribute("role", "menu", null);

            final ClientBehaviorContext behaviorContext =
                    ClientBehaviorContext.createClientBehaviorContext(context,
                            tableHeader, "click", tableHeader.getClientId(context), null);

            int columnNumber = 0;
            for (HtmlColumn cachedColumn : this.cachedTableComponent.getCachedColumns()) {
                writer.startElement("li", tableHeader);
                writer.startElement("label", tableHeader);
                writer.writeAttribute("class", "checkbox", null);
                writer.startElement("input", tableHeader);
                writer.writeAttribute("type", "checkbox", null);
                writer.writeAttribute("columnNumber", "" + columnNumber, null);

                final String jQueryPluginCall = RenderUtils.createJQueryPluginCall(this.cachedTableComponent.getClientId(), "toggleColumnVisibilty({columnIndex:'" + columnNumber + "'})");

                final Map<String, List<ClientBehavior>> behaviors = tableHeader.getClientBehaviors();
                if (behaviors.containsKey("click")) {
                    final AjaxBehavior clientBehavior = (AjaxBehavior) behaviors.get("click").get(0);
                    final String click = clientBehavior.getScript(behaviorContext);

                    if (StringUtils.isNotEmpty(click)) {
                        // ajax tag is enabled
                        final AjaxBehavior ajaxBehavior = new AjaxBehavior();
                        ajaxBehavior.setRender(clientBehavior.getRender());
                        if (tableHeader.isAjaxDisableRenderRegionsOnRequest()) {
                            ajaxBehavior.setOnevent(this.getOnEventListenerName(this.cachedTableComponent));
                        }
                        final String ajaxBehaviorScript = ajaxBehavior.getScript(behaviorContext);

                        //final String ajaxCall = this.createAjaxCall(tableHeader, "toggle_" + columnNumber, "0");

                        final String correctedEventName = ajaxBehaviorScript.replace(",'click',", ",'toggle_" + columnNumber + "',");
                        writer.writeAttribute("onclick", correctedEventName + ";" + jQueryPluginCall, null);
                    } else {
                        // ajax tag is disabled
                        writer.writeAttribute("onclick", jQueryPluginCall, null);
                    }
                } else {
                    // no ajax tag is used
                    writer.writeAttribute("onclick", jQueryPluginCall, null);
                }
                if (!this.isHideColumn(this.cachedTableComponent, cachedColumn)) {
                    writer.writeAttribute("checked", "checked", null);
                }
                writer.endElement("input");
                writer.writeText(cachedColumn.getLabel(), null);
                writer.endElement("label");
                writer.endElement("li");
                columnNumber++;
            }
            writer.endElement("ul");

            writer.endElement("div");
        }
    }

    private boolean isHideColumn(final HtmlTable table, final HtmlColumn column) {
        if (table.getTableColumnDisplayModel() != null) {
            final Boolean hideColumn = table.getTableColumnDisplayModel().isColumnHidden(column.getId());
            if (hideColumn != null) {
                return hideColumn;
            }
        }
        return column.isHideColumn();
    }

    private void renderTableToolbarRefreshButton(final ResponseWriter writer,
                                                 final HtmlTableToolbar tableToolbar) throws IOException {
        if (tableToolbar.isShowRefreshButton()) {
            writer.startElement("a", tableToolbar);
            writer.writeAttribute("class", "btn btn-default", null);
            writer.writeAttribute("role", "button", null);
            writer.writeAttribute("title", "Refresh table", null);

            final String onEvent = tableToolbar.isAjaxDisableRenderRegionsOnRequest() ? this.getOnEventListenerName(this.cachedTableComponent) : null;
            final AjaxCall ajaxCall = new AjaxCall(tableToolbar, "refresh", onEvent);
            writer.writeAttribute("onclick", ajaxCall.createJavaScriptCall(), null);

            writer.startElement("i", tableToolbar);
            writer.writeAttribute("class", "glyphicon glyphicon-refresh", null);
            writer.endElement("i");
            writer.endElement("a");
        }
    }

    private String getOnEventListenerName(final UIComponent component) {
        final char separatorChar = UINamingContainer.getSeparatorChar(FacesContext.getCurrentInstance());
        return "refreshTable" + "_" + component.getClientId().replace(separatorChar + "", "_");
    }
}
