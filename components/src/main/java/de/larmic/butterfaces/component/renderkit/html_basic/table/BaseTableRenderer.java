/*
 * Copyright Lars Michaelis and Stephan Zerhusen 2016.
 * Distributed under the MIT License.
 * (See accompanying file README.md file or copy at http://opensource.org/licenses/MIT)
 */
package de.larmic.butterfaces.component.renderkit.html_basic.table;

import de.larmic.butterfaces.component.base.renderer.HtmlBasicRenderer;
import de.larmic.butterfaces.component.html.table.HtmlTable;

import javax.faces.component.UIComponent;
import javax.faces.component.UIData;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import java.io.IOException;

/**
 * Base class for concrete table renderes.
 *
 * @author Lars Michaelis
 */
public abstract class BaseTableRenderer extends HtmlBasicRenderer {

    @Override
    public void encodeBegin(FacesContext context, UIComponent component)
            throws IOException {
        if (!component.isRendered()) {
            return;
        }

        final UIData data = (UIData) component;
        data.setRowIndex(-1);

        // Render the beginning of the table
        ResponseWriter writer = context.getResponseWriter();

        renderTableStart(context, component, writer);

        // Render the header facets (if any)
        renderHeader(context, component, writer);
    }

    @Override
    public void encodeChildren(FacesContext context, UIComponent component)
            throws IOException {
        if (!component.isRendered()) {
            return;
        }

        final UIData data = (UIData) component;
        final ResponseWriter writer = context.getResponseWriter();

        // Iterate over the rows of data that are provided
        int processed = 0;
        int rowIndex = data.getFirst() - 1;
        int rows = data.getRows();

        writer.startElement("tbody", component);

        while (true) {
            // Have we displayed the requested number of rows?
            if ((rows > 0) && (++processed > rows)) {
                break;
            }
            // Select the current row
            data.setRowIndex(++rowIndex);
            if (!data.isRowAvailable()) {
                break; // Scrolled past the last row
            }

            // Render the beginning of this row
            renderRowStart(context, component, writer);

            // Render the row content
            renderRow(context, (HtmlTable) component, null, writer);

            // Render the ending of this row
            renderRowEnd(context, component, writer);
        }

        writer.endElement("tbody");

        // Clean up after ourselves
        data.setRowIndex(-1);
    }

    @Override
    public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
        if (!component.isRendered()) {
            return;
        }

        ((HtmlTable) component).clearMetaInfo(context, component);
        ((UIData) component).setRowIndex(-1);

        renderTableEnd(context, component, context.getResponseWriter());
    }

    @Override
    public boolean getRendersChildren() {
        return true;
    }

    protected abstract void renderHeader(FacesContext context, UIComponent table, ResponseWriter writer) throws IOException;

    protected abstract void renderRow(FacesContext context, HtmlTable table, UIComponent row, ResponseWriter writer) throws IOException;

    protected void renderTableStart(FacesContext context, UIComponent table, ResponseWriter writer) throws IOException {
        writer.startElement("table", table);
    }

    protected void renderTableEnd(FacesContext context, UIComponent table, ResponseWriter writer) throws IOException {
        writer.endElement("table");
    }

    protected void renderRowStart(FacesContext context, UIComponent table, ResponseWriter writer) throws IOException {
        writer.startElement("tr", table);
    }

    protected void renderRowEnd(FacesContext context, UIComponent table, ResponseWriter writer) throws IOException {
        writer.endElement("tr");
    }
}
