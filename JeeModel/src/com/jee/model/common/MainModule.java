package com.jee.model.common;


public enum MainModule {
	UploadModule, VoucherAdministration, Common;
	
	public String getI18nKey(){
		switch(this){
		case UploadModule:
			return "admin.module.name";
		case VoucherAdministration:
			return "userAdmin.module.name";
		default:
			break;
		}
		return "";
	}
}
