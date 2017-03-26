package com.desenvolvimento.pos.entity;

import java.io.Serializable;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

@MappedSuperclass
public abstract class BaseEntity<PK> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Version
	private Integer version;
	
	public abstract PK getId();
	
	/**
	 * Obtém a versão da entidade.
	 * 
	 * @return
	 */
	public Integer getVersion() {
		return version;
	}
	
	/**
	 * Verifica se a entidade possui identificador.
	 * 
	 * @return
	 */
	public boolean isTransient() {
		return getId() == null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
		return result;
	}

	@Override
	@SuppressWarnings("rawtypes")
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BaseEntity other = (BaseEntity) obj;
		if (getId() == null) {
			if (other.getId() != null)
				return false;
		} else if (!getId().equals(other.getId()))
			return false;
		return true;
	}
	
}
