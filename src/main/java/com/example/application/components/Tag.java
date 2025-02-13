package com.example.application.components;

import com.example.application.utilities.TextColor;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.avatar.AvatarVariant;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.theme.lumo.LumoUtility;

public class Tag extends Span {

	private TextColor textColor;
	private Layout prefix;
	private Text text;

	public Tag(Component prefix, String text, TextColor textColor) {
		addClassNames(
				LumoUtility.AlignItems.CENTER, LumoUtility.Display.FLEX, LumoUtility.FontSize.SMALL,
				LumoUtility.Gap.SMALL
		);
		setTextColor(textColor);

		this.prefix = new Layout();
		this.prefix.setVisible(false);
		setPrefix(prefix);

		this.text = new Text(text);

		add(this.prefix, this.text);
	}

	public Tag(Component prefix, String text) {
		this(prefix, text, TextColor.SECONDARY);
	}

	public Tag(VaadinIcon icon, String text, TextColor textColor) {
		this(icon.create(), text, textColor);
	}

	public Tag(VaadinIcon icon, String text) {
		this(icon.create(), text, TextColor.SECONDARY);
	}

	public Tag(String text) {
		this((Component) null, text, TextColor.SECONDARY);
	}

	/**
	 * Sets the prefix.
	 */
	public void setPrefix(Component... components) {
		this.prefix.removeAll();
		if (components != null) {
			for (Component component : components) {
				if (component != null) {
					if (component instanceof Icon) {
						((Icon) component).addClassNames(LumoUtility.IconSize.SMALL);
					}
					if (component instanceof Avatar) {
						((Avatar) component).addThemeVariants(AvatarVariant.LUMO_XSMALL);
					}
					this.prefix.add(component);
				}
			}
		}
		this.prefix.setVisible(this.prefix.getComponentCount() > 0);
	}

	/**
	 * Sets the text color.
	 */
	public void setTextColor(TextColor textColor) {
		if (this.textColor != null) {
			removeClassNames(this.textColor.getClassName());
		}
		addClassNames(textColor.getClassName());
		this.textColor = textColor;
	}

}
