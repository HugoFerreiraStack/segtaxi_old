package br.com.seg.maxipago.datacontract.transactional;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

public class Phones {
	
	@XStreamAsAttribute
	private int phoneCount;
	
	@XStreamImplicit
	private List<Phone> phone;
	
	public List<Phone> getPhone() {
		return phone;
	}
	
	public int getPhoneCount() {
		return phoneCount;
	}
	
	public void setPhone(List<Phone> phone) {
		this.phone = phone;
	}
	
	public void setPhoneCount(int phoneCount) {
		this.phoneCount = phoneCount;
	}
}