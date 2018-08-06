package eu.execom.pomodoroTeam.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PomodoroEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private Date dateTimeOn;

    @Column(nullable = false)
    private Date dateTimeOff;

    @JsonBackReference
    @OneToMany(mappedBy = "pomodoro", fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH})
    private List<UserEntity> user = new ArrayList<>();

    public PomodoroEntity() {
        super();
        // TODO Auto-generated constructor stub
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDateTimeOn() {
        return dateTimeOn;
    }

    public void setDateTimeOn(Date dateTimeOn) {
        this.dateTimeOn = dateTimeOn;
    }

    public Date getDateTimeOff() {
        return dateTimeOff;
    }

    public void setDateTimeOff(Date dateTimeOff) {
        this.dateTimeOff = dateTimeOff;
    }

    public List<UserEntity> getUser() {
        return user;
    }

    public void setUser(List<UserEntity> user) {
        this.user = user;
    }
}
