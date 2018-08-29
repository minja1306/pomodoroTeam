package eu.execom.pomodoroTeam.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Data
@Entity
@NoArgsConstructor
public class TeamEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToMany(cascade = CascadeType.REFRESH)
    @JoinTable(name = "User_Team", joinColumns = {
            @JoinColumn(name = "Team_id", nullable = false, updatable = false)}, inverseJoinColumns = {
            @JoinColumn(name = "User_id", nullable = false, updatable = false)})
    private List<UserEntity> user = new ArrayList<>();
}
