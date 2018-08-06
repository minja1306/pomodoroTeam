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
import javax.persistence.OneToMany;




@Entity
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH})
    private List<PomodoroEntity> pomodoros = new ArrayList<>();

 
   
    
    @ManyToMany(cascade = CascadeType.REFRESH)
    @JoinTable(name = "User_Team", joinColumns = {
            @JoinColumn(name = "User_id", nullable = false, updatable = false)}, inverseJoinColumns = {
                    @JoinColumn(name = "Team_id", nullable = false, updatable = false)})
    private List<TeamEntity> teams = new ArrayList<>();

    public UserEntity() {}

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

   
     public List<PomodoroEntity> getPomodoros() {
		return pomodoros;
	}

	public void setPomodoros(List<PomodoroEntity> pomodoros) {
		this.pomodoros = pomodoros;
	}

	public List<TeamEntity> getTeams() {
		return teams;
	}

	public void setTeams(List<TeamEntity> teams) {
		this.teams = teams;
	}


}
