package com.granium.domain.psql.entity

import com.granium.domain.psql.types.json.JsonBinaryType
import org.hibernate.annotations.Type
import org.hibernate.annotations.TypeDef
import org.hibernate.validator.constraints.NotEmpty
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import javax.persistence.*

/**
 * Created by Sasha.Chepurnoi on 06.12.16.
 */

@Entity
@Table(name = "users")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType::class)
class User : UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGen")
  @SequenceGenerator(name = "sequenceGen", sequenceName = "users_id_seq", allocationSize = 1)
  @Column(name = "id")
  var id: Int = 0

  @NotEmpty
  @Column(name = "login")
  var login: String = ""

  @NotEmpty
  @Column(name = "password")
  var pass: String = ""

  @Column(name = "role")
  var role: String = "ROLE_USER"

  @Column(name = "active")
  var active: Boolean = true

  @Type(type = "jsonb")
  @Column(name = "following", columnDefinition = "jsonb")
  var following: MutableSet<String> = mutableSetOf()

  @Type(type = "jsonb")
  @Column(name = "followers", columnDefinition = "jsonb")
  var followers: MutableSet<String> = mutableSetOf()

  override fun getUsername(): String = login

  override fun isCredentialsNonExpired(): Boolean = true

  override fun isAccountNonExpired(): Boolean = true

  override fun isAccountNonLocked(): Boolean = true

  override fun getAuthorities(): MutableCollection<out GrantedAuthority> = mutableListOf(SimpleGrantedAuthority("user"))

  override fun isEnabled(): Boolean = true

  override fun getPassword(): String = pass
}
