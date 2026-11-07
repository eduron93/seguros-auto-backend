package hn.demo.seguros.infrastructure;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "app_users")
public class UserEntity {


 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 public Long id;


 @Column(
         unique = true,
         nullable = false
 )
 public String username;


 @Column(nullable = false)
 public String passwordHash;


 protected UserEntity() {
 }


 public UserEntity(
         String username,
         String passwordHash
 ) {

  this.username = username;
  this.passwordHash = passwordHash;
 }
}
