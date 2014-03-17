package app.twitter.models;


public class Twt {

	public Twt(Builder builder) {
		super();
		this.id				= builder.id;
		this.created_at		= builder.created_at;
		this.modified_at	= builder.modified_at;
		this.text			= builder.text;
		this.twt_created_at	= builder.twt_created_at;
		this.twt_id			= builder.twt_id;
		this.uploaded_at	= builder.uploaded_at;
	}



	long id;					// 0
	String created_at;			// 1
	String modified_at;			// 2

	String text;			// 3
	String twt_created_at;			// 4
	long twt_id;				// 5
	
	String uploaded_at;			// 6

	public long getId() {
		return id;
	}

	public String getCreated_at() {
		return created_at;
	}

	public String getModified_at() {
		return modified_at;
	}

	public String getText() {
		return text;
	}

	public String getTwtCreatedAt() {
		return twt_created_at;
	}

	public long getTwtId() {
		return twt_id;
	}

	public String getUploaded_at() {
		return uploaded_at;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	public void setModified_at(String modified_at) {
		this.modified_at = modified_at;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setTwtCreatedAt(String twt_created_at) {
		this.twt_created_at = twt_created_at;
	}

	public void setTwtId(long twt_id) {
		this.twt_id = twt_id;
	}

	public void setUploaded_at(String uploaded_at) {
		this.uploaded_at = uploaded_at;
	}
	
	/*********************************
	 * Getter/Setter
	 *********************************/
	
	
	/*********************************
	 * Class: Builder
	 *********************************/
	public static class Builder {
		
		long id;					// 0
		String created_at;			// 1
		String modified_at;			// 2

		String text;			// 3
		String twt_created_at;			// 4
		long twt_id;				// 5
		
		String uploaded_at;			// 6
		
		public Twt build() {
			
			return new Twt(this);
			
		}

		public Builder setId(long id) {
			this.id = id;	return this;
		}

		public Builder setCreated_at(String created_at) {
			this.created_at = created_at;	return this;
		}

		public Builder setModified_at(String modified_at) {
			this.modified_at = modified_at;	return this;
		}

		public Builder setText(String text) {
			this.text = text;	return this;
		}

		public Builder setTwtCreatedAt(String twt_created_at) {
			this.twt_created_at = twt_created_at;	return this;
		}

		public Builder setTwtId(long twt_id) {
			this.twt_id = twt_id;	return this;
		}

		public Builder setUploaded_at(String uploaded_at) {
			this.uploaded_at = uploaded_at;	return this;
		}

	}//public static class Builder

	
}
