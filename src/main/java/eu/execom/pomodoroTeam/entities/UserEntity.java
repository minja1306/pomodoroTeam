package eu.execom.pomodoroTeam.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Version
    private Integer version;

    @JsonManagedReference
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "pomodoro")
    private PomodoroEntity pomodoro;

    @JsonManagedReference
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinTable(name = "User_Team", joinColumns = {
            @JoinColumn(name = "User_id", nullable = false, updatable = false)}, inverseJoinColumns = {
                    @JoinColumn(name = "Team_id", nullable = false, updatable = false)})
    private List<TeamEntity> team = new ArrayList<>();

    public UserEntity() {
        super();
        // TODO Auto-generated constructor stub
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public PomodoroEntity getPomodoro() {
        return pomodoro;
    }

    public void setPomodoro(PomodoroEntity pomodoro) {
        this.pomodoro = pomodoro;
    }

    public List<TeamEntity> getTeam() {
        return team;
    }

    public void setTeam(List<TeamEntity> team) {
        this.team = team;
    }

}
