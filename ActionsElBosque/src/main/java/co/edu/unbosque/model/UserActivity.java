package co.edu.unbosque.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "user_activity_logs")
public class UserActivity {

	@Id
	private String id;

	@Field(type = FieldType.Long)
	private Long userId;

	@Field(type = FieldType.Text)
	private String action;

	@Field(type = FieldType.Text)
	private String details;

	@Field(type = FieldType.Date)
	private Instant timestamp;
}
