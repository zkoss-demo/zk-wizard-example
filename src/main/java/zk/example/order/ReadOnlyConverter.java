package zk.example.order;

import org.zkoss.bind.BindContext;
import org.zkoss.bind.Converter;
import org.zkoss.zk.ui.Component;

public interface ReadOnlyConverter<B, U> extends Converter<U, B, Component> {
	@Override
	default U coerceToUi(B beanProp, Component component, BindContext ctx) {
		return this.convert(beanProp);
	}

	@Override
	default B coerceToBean(U compAttr, Component component, BindContext ctx) {
		throw new UnsupportedOperationException("read only");
	}
	
	U convert(B beanProp);
}