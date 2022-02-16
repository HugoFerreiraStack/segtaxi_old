package br.com.seg.econotaxi.vo;

public class CodigoSmsVo {

	private String email;
    private String celular;
    private String codigo;
    private String hashApp;

    public String getEmail() {
		return email;
	}
    
    public void setEmail(String email) {
		this.email = email;
	}
    
    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

	public String getHashApp() {
		return hashApp;
	}

	public void setHashApp(String hashApp) {
		this.hashApp = hashApp;
	}
    
}
