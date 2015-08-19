function (out) {
	var uuid = this.uuid, content = this.domContent_();
	out.push('<label', this.domAttrs_(), ' for="', uuid,'-real">',
			 '<input type="checkbox" id="', uuid, '-real"', this.contentAttrs_(), '/>',
			 content,'</label>');
}