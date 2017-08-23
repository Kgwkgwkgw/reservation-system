package naverest.reservation.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class GoogleLoginProfile implements Serializable {
		private String id;
		private String displayName;
		private Map<String, String> name;
		private List<Map<String, String>> emails;
		
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getDisplayName() {
			return displayName;
		}
		public void setDisplayName(String displayName) {
			this.displayName = displayName;
		}
		public Map<String, String> getName() {
			return name;
		}
		public void setName(Map<String, String> name) {
			this.name = name;
		}
		public List<Map<String, String>> getEmails() {
			return emails;
		}
		public void setEmails(List<Map<String, String>> emails) {
			this.emails = emails;
		}
		@Override
		public String toString() {
		  return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
		}
		
}
