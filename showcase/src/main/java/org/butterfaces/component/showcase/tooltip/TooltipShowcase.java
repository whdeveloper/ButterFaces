package org.butterfaces.component.showcase.tooltip;

import org.butterfaces.component.showcase.AbstractCodeShowcase;
import org.butterfaces.component.showcase.example.AbstractCodeExample;
import org.butterfaces.component.showcase.example.WebXmlCodeExample;
import org.butterfaces.component.showcase.example.XhtmlCodeExample;
import org.butterfaces.model.tree.EnumTreeBoxWrapper;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
@SuppressWarnings("serial")
public class TooltipShowcase extends AbstractCodeShowcase implements Serializable {

    private TooltipType selectedTooltTipType = TooltipType.A;
    private String title = "custom title";
    private String trigger = "hover";
    private String viewport = "body";
    private String placement;
    private String textFieldValue;

    @Override
    public void buildCodeExamples(final List<AbstractCodeExample> codeExamples) {
        final XhtmlCodeExample xhtmlCodeExample = new XhtmlCodeExample(false);

        if (selectedTooltTipType == TooltipType.A) {
            xhtmlCodeExample.setWrappedByForm(false);
            xhtmlCodeExample.appendInnerContent("        <a id=\"btn\" class=\"btn btn-outline-secondary\">" + trigger + " me!</a>");

            xhtmlCodeExample.appendInnerContent("        <b:tooltip id=\"input\"");
            xhtmlCodeExample.appendInnerContent("                   for=\"#btn\"");
            xhtmlCodeExample.appendInnerContent("                   title=\"" + title + "\"");
            xhtmlCodeExample.appendInnerContent("                   trigger=\"" + trigger + "\"");
            xhtmlCodeExample.appendInnerContent("                   placement=\"" + placement + "\"");
            xhtmlCodeExample.appendInnerContent("                   viewport=\"" + viewport + "\"");
            xhtmlCodeExample.appendInnerContent("                   rendered=\"" + this.isRendered() + "\">");
            xhtmlCodeExample.appendInnerContent("            <strong>some text stuff</strong>");
            xhtmlCodeExample.appendInnerContent("            <br />");
            xhtmlCodeExample.appendInnerContent("            <h:panelGroup>");
            xhtmlCodeExample.appendInnerContent("                Lorem ipsum dolor sit amet, consectetuer ...");
            xhtmlCodeExample.appendInnerContent("            </h:panelGroup>");
            xhtmlCodeExample.appendInnerContent("        </b:tooltip>", false);
        } else if (selectedTooltTipType == TooltipType.COMMAND_LINK) {
            xhtmlCodeExample.appendInnerContent("        <b:commandLink value=\"" + trigger + " me!\"");
            xhtmlCodeExample.appendInnerContent("                       styleClass=\"btn btn-outline-secondary\">");
            xhtmlCodeExample.appendInnerContent("            <b:tooltip title=\"" + title + "\"");
            xhtmlCodeExample.appendInnerContent("                       trigger=\"" + trigger + "\"");
            xhtmlCodeExample.appendInnerContent("                       placement=\"" + placement + "\"");
            xhtmlCodeExample.appendInnerContent("                       viewport=\"" + viewport + "\"");
            xhtmlCodeExample.appendInnerContent("                       rendered=\"" + this.isRendered() + "\">");
            xhtmlCodeExample.appendInnerContent("                <strong>some text stuff</strong>");
            xhtmlCodeExample.appendInnerContent("                <br />");
            xhtmlCodeExample.appendInnerContent("                <h:panelGroup>");
            xhtmlCodeExample.appendInnerContent("                    Lorem ipsum dolor sit amet, consectetuer ...");
            xhtmlCodeExample.appendInnerContent("                </h:panelGroup>");
            xhtmlCodeExample.appendInnerContent("            </b:tooltip>");
            xhtmlCodeExample.appendInnerContent("        </b:commandLink>", false);
        } else if (selectedTooltTipType == TooltipType.READONLY_TEXT) {
            xhtmlCodeExample.appendInnerContent("        <b:text value=\"short text\"");
            xhtmlCodeExample.appendInnerContent("                label=\"" + trigger + " me!\"");
            xhtmlCodeExample.appendInnerContent("                readonly=\"true\">");
            xhtmlCodeExample.appendInnerContent("            <f:ajax event=\"blur\"");
            xhtmlCodeExample.appendInnerContent("                    execute=\"@this\"");
            xhtmlCodeExample.appendInnerContent("                    render=\"@this\" />");
            xhtmlCodeExample.appendInnerContent("            <f:validateLength minimum=\"2\"");
            xhtmlCodeExample.appendInnerContent("                              maximum=\"10\" />");
            xhtmlCodeExample.appendInnerContent("            <b:tooltip title=\"" + title + "\"");
            xhtmlCodeExample.appendInnerContent("                       trigger=\"" + trigger + "\"");
            xhtmlCodeExample.appendInnerContent("                       placement=\"" + placement + "\"");
            xhtmlCodeExample.appendInnerContent("                       viewport=\"" + viewport + "\"");
            xhtmlCodeExample.appendInnerContent("                       rendered=\"" + this.isRendered() + "\">");
            xhtmlCodeExample.appendInnerContent("                <strong>some text stuff</strong>");
            xhtmlCodeExample.appendInnerContent("                <br />");
            xhtmlCodeExample.appendInnerContent("                <h:panelGroup>");
            xhtmlCodeExample.appendInnerContent("                    Lorem ipsum dolor sit amet, consectetuer ...");
            xhtmlCodeExample.appendInnerContent("                </h:panelGroup>");
            xhtmlCodeExample.appendInnerContent("            </b:tooltip>");
            xhtmlCodeExample.appendInnerContent("        </b:text>", false);
        } else {
            xhtmlCodeExample.appendInnerContent("        <b:text value=\"#{myBean.value}\"");
            xhtmlCodeExample.appendInnerContent("                label=\"" + trigger + " me!\"");
            xhtmlCodeExample.appendInnerContent("                placeholder=\"Insert text and blur me...\">");
            xhtmlCodeExample.appendInnerContent("            <f:ajax event=\"blur\"");
            xhtmlCodeExample.appendInnerContent("                    execute=\"@this\"");
            xhtmlCodeExample.appendInnerContent("                    render=\"@this\" />");
            xhtmlCodeExample.appendInnerContent("            <f:validateLength minimum=\"2\"");
            xhtmlCodeExample.appendInnerContent("                              maximum=\"10\" />");
            xhtmlCodeExample.appendInnerContent("            <b:tooltip title=\"" + title + "\"");
            xhtmlCodeExample.appendInnerContent("                       trigger=\"" + trigger + "\"");
            xhtmlCodeExample.appendInnerContent("                       placement=\"" + placement + "\"");
            xhtmlCodeExample.appendInnerContent("                       viewport=\"" + viewport + "\"");
            xhtmlCodeExample.appendInnerContent("                       rendered=\"" + this.isRendered() + "\">");
            xhtmlCodeExample.appendInnerContent("                <strong>some text stuff</strong>");
            xhtmlCodeExample.appendInnerContent("                <br />");
            xhtmlCodeExample.appendInnerContent("                <h:panelGroup>");
            xhtmlCodeExample.appendInnerContent("                    Lorem ipsum dolor sit amet, consectetuer ...");
            xhtmlCodeExample.appendInnerContent("                </h:panelGroup>");
            xhtmlCodeExample.appendInnerContent("            </b:tooltip>");
            xhtmlCodeExample.appendInnerContent("        </b:text>", false);
        }

        codeExamples.add(xhtmlCodeExample);
        codeExamples.add(createWebXmlExample());
    }

    private AbstractCodeExample createWebXmlExample() {
        final WebXmlCodeExample webXmlCodeExample = new WebXmlCodeExample("web.xml", "webxml");

        webXmlCodeExample.appendInnerContent("  <!-- If you want to position tooltips on label instead of component -->");
        webXmlCodeExample.appendInnerContent("  <!-- default is false -->");
        webXmlCodeExample.appendInnerContent("  <context-param>");
        webXmlCodeExample.appendInnerContent("     <param-name>org.butterfaces.tooltip.position.label</param-name>");
        webXmlCodeExample.appendInnerContent("     <param-value>true</param-value>");
        webXmlCodeExample.appendInnerContent("  </context-param>");

        return webXmlCodeExample;
    }

    public List<EnumTreeBoxWrapper> getTooltipTypes() {
        final List<EnumTreeBoxWrapper> items = new ArrayList<>();

        for (final TooltipType type : TooltipType.values()) {
            items.add(new EnumTreeBoxWrapper(type, type.label));
        }
        return items;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTrigger() {
        return trigger;
    }

    public void setTrigger(String trigger) {
        this.trigger = trigger;
    }

    public String getPlacement() {
        return placement;
    }

    public void setPlacement(String placement) {
        this.placement = placement;
    }

    public TooltipType getSelectedTooltTipType() {
        return selectedTooltTipType;
    }

    public void setSelectedTooltTipType(TooltipType selectedTooltTipType) {
        this.selectedTooltTipType = selectedTooltTipType;
    }

    public String getTextFieldValue() {
        return textFieldValue;
    }

    public void setTextFieldValue(String textFieldValue) {
        this.textFieldValue = textFieldValue;
    }

    public String getViewport() {
        return viewport;
    }

    public void setViewport(String viewport) {
        this.viewport = viewport;
    }
}
