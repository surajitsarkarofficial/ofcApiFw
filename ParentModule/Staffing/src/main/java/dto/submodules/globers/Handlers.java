package dto.submodules.globers;

public class Handlers {

	public String handlerId;
	public String handlerName;

	/**
	 * @param handlerId
	 * @param handlerName
	 */
	public Handlers(String handlerId, String handlerName) {
		super();
		this.handlerId = handlerId;
		this.handlerName = handlerName;
	}

	/**
	 * @return the handlerId
	 */
	public String getHandlerId() {
		return handlerId;
	}

	/**
	 * @param handlerId the handlerId to set
	 */
	public void setHandlerId(String handlerId) {
		this.handlerId = handlerId;
	}

	/**
	 * @return the handlerName
	 */
	public String getHandlerName() {
		return handlerName;
	}

	/**
	 * @param handlerName the handlerName to set
	 */
	public void setHandlerName(String handlerName) {
		this.handlerName = handlerName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((handlerId == null) ? 0 : handlerId.hashCode());
		result = prime * result + ((handlerName == null) ? 0 : handlerName.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Handlers other = (Handlers) obj;
		if (handlerId == null) {
			if (other.handlerId != null)
				return false;
		} else if (!handlerId.equals(other.handlerId))
			return false;
		if (handlerName == null) {
			if (other.handlerName != null)
				return false;
		} else if (!handlerName.equals(other.handlerName))
			return false;
		return true;
	}
}
