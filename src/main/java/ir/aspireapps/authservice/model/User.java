package ir.aspireapps.authservice.model;

import java.time.Instant;
import java.util.UUID;

import ir.aspireapps.authservice.dto.user.UserUpdateDetailsRequest;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "users",
		indexes = {
				@Index(name = "idx_user_email", columnList = "email")
			}
		)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@SQLDelete(sql = "UPDATE users SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@EqualsAndHashCode.Include
	private UUID id;
	
	@Column(nullable = false, unique = false)
	@ToString.Include
	private String email;
	
	@Column(nullable = false, length = 255)
	private String password;
	
	@Column(nullable = false, length = 150)
	@ToString.Include
	private String firstName;
	
	@Column(nullable = false, length = 250)
	@ToString.Include
	private String lastName;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Role role = Role.USER;
	
	@Column(name = "created_at", nullable = false)
	@CreationTimestamp
	private Instant createdAt;
	
	@Column(name = "updated_at", nullable = false)
	@UpdateTimestamp
	private Instant updatedAt;
	
	@Column(name = "deleted_at", nullable = true)
	private Instant deleted_at;

	public void updateDetails(UserUpdateDetailsRequest request){
		this.firstName = request.firstName();
		this.lastName = request.lastName();
	}
}
