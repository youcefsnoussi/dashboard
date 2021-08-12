package com.commercial.wrapperObjects;

import java.util.ArrayList;
import java.util.List;

import com.commercial.entities.schema.profoma_cmd_bl_fact.proforma_detail;

public class ProformaDetailsDto {
	
	private List<proforma_detail> proforma_details = new ArrayList<>();
	
	public ProformaDetailsDto() {
		// TODO Auto-generated constructor stub
	}

	public ProformaDetailsDto(List<proforma_detail> proformaDetails) {
		super();
		this.proforma_details = proformaDetails;
	}
	
	public void addDetails(proforma_detail detail) {
		
		this.proforma_details.add(detail);
		
	}

	public List<proforma_detail> getProformaDetails() {
		return proforma_details;
	}

	public void setProformaDetails(List<proforma_detail> proformaDetails) {
		proforma_details = proformaDetails;
	}
	
}
