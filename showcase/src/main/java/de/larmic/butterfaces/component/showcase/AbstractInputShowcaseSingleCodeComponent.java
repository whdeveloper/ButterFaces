package de.larmic.butterfaces.component.showcase;

import javax.faces.model.SelectItem;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractInputShowcaseSingleCodeComponent extends AbstractShowcaseSingleCodeComponent {

    protected static final String DEFAULT_NUMBER_PLACEHOLDER = "Enter number...";
    protected static final String DEFAULT_TEXT_PLACEHOLDER = "Enter text...";

    private Object value;
    private String label = "label";
    private String tooltip = "tooltip";
    private String badgeText = null;
    private boolean readonly;
    private boolean required;
    private boolean validation;
    private boolean hideLabel;
    private AjaxType ajaxType = AjaxType.NONE;
    private String inputStyleClass = null;
    private String labelStyleClass = null;
    private String styleClass = null;

    public AbstractInputShowcaseSingleCodeComponent() {
        this.value = this.initValue();
    }

    /**
     * @return specific value object (i.e. a String, Date or Enum) that is
     * showing after loading showcase.
     */
    protected abstract Object initValue();

    /**
     * @return a readable value of field value (maybe translated enum or
     * something).
     */
    public abstract String getReadableValue();

    public boolean isAjax() {
        return AjaxType.NONE != this.getAjaxType();
    }

    public List<SelectItem> getAjaxTypes() {
        final List<SelectItem> items = new ArrayList<>();

        for (final AjaxType type : AjaxType.values()) {
            items.add(new SelectItem(type, type.label));
        }
        return items;
    }

    public List<SelectItem> getInputStyleClasses() {
        final List<SelectItem> items = new ArrayList<>();

        items.add(new SelectItem("col-sm-1", "col-sm-1"));
        items.add(new SelectItem("col-sm-2", "col-sm-2"));
        items.add(new SelectItem("col-sm-3", "col-sm-3"));
        items.add(new SelectItem("col-sm-4", "col-sm-4"));
        items.add(new SelectItem("col-sm-5", "col-sm-5"));
        items.add(new SelectItem("col-sm-6", "col-sm-6"));
        items.add(new SelectItem("col-sm-7", "col-sm-7"));
        items.add(new SelectItem("col-sm-8", "col-sm-8"));
        items.add(new SelectItem("col-sm-9", "col-sm-9"));
        items.add(new SelectItem(null, "default (col-sm-10)"));
        items.add(new SelectItem("col-sm-9", "col-sm-11"));
        items.add(new SelectItem("col-sm-9", "col-sm-12"));

        return items;
    }

    public List<SelectItem> getLabelStyleClasses() {
        final List<SelectItem> items = new ArrayList<>();

        items.add(new SelectItem("col-sm-1", "col-sm-1"));
        items.add(new SelectItem(null, "default (col-sm-2)"));
        items.add(new SelectItem("col-sm-3", "col-sm-3"));
        items.add(new SelectItem("col-sm-4", "col-sm-4"));
        items.add(new SelectItem("col-sm-5", "col-sm-5"));
        items.add(new SelectItem("col-sm-6", "col-sm-6"));
        items.add(new SelectItem("col-sm-7", "col-sm-7"));
        items.add(new SelectItem("col-sm-8", "col-sm-8"));
        items.add(new SelectItem("col-sm-9", "col-sm-10"));
        items.add(new SelectItem("col-sm-9", "col-sm-11"));
        items.add(new SelectItem("col-sm-9", "col-sm-12"));

        return items;
    }

    public List<SelectItem> getStyleClasses() {
        final List<SelectItem> items = new ArrayList<>();

        items.add(new SelectItem(null, "default (null)"));
        items.add(new SelectItem("some-demo-class", "some-demo-class"));

        return items;
    }



    public void createAjaxXhtml(final StringBuilder sb, final String event) {
        if (this.isAjax()) {
            final String execute = AjaxType.THIS == this.ajaxType ? "@this" : "input";
            sb.append("            <f:ajax event=\"" + event + "\"  execute=\"" + execute + "\" render=\"output\"/>\n");
        }
    }

    public void createOutputXhtml(final StringBuilder sb) {
        if (this.isAjax()) {
            sb.append("\n");
            sb.append("\n");
            sb.append("        <h:outputText id=\"output\" value=\"" + this.getValue() + "\"/>");
        }
    }

    @Override
    public String getCss() {
        return super.getCss();
    }

    public void submit() {

    }

    public Object getValue() {
        return this.value;
    }

    public void setValue(final Object value) {
        this.value = value;
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(final String label) {
        this.label = label;
    }

    public String getBadgeText() {
        return badgeText;
    }

    public void setBadgeText(String badgeText) {
        this.badgeText = badgeText;
    }

    public String getTooltip() {
        return this.tooltip;
    }

    public void setTooltip(final String tooltip) {
        this.tooltip = tooltip;
    }

    public boolean isReadonly() {
        return this.readonly;
    }

    public void setReadonly(final boolean readonly) {
        this.readonly = readonly;
    }

    public boolean isRequired() {
        return this.required;
    }

    public void setRequired(final boolean required) {
        this.required = required;
    }

    public boolean isValidation() {
        return this.validation;
    }

    public void setValidation(final boolean validation) {
        this.validation = validation;
    }

    public AjaxType getAjaxType() {
        return this.ajaxType;
    }

    public void setAjaxType(final AjaxType ajax) {
        this.ajaxType = ajax;
    }

    public boolean isHideLabel() {
        return hideLabel;
    }

    public void setHideLabel(boolean hideLabel) {
        this.hideLabel = hideLabel;
    }

    public String getInputStyleClass() {
        return inputStyleClass;
    }

    public void setInputStyleClass(String inputStyleClass) {
        this.inputStyleClass = inputStyleClass;
    }

    public String getLabelStyleClass() {
        return labelStyleClass;
    }

    public void setLabelStyleClass(String labelStyleClass) {
        this.labelStyleClass = labelStyleClass;
    }

    public String getStyleClass() {
        return styleClass;
    }

    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }
}