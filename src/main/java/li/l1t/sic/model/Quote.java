package li.l1t.sic.model;

import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;

/**
 * Represents a sic quote, associated with a person.
 *
 * @author <a href="http://xxyy.github.io/">xxyy</a>
 * @since 2016-02-14
 */
@Entity
@Table(name = "sic_quote")
public class Quote extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "person_id", updatable = false, nullable = false)
    private Person person;

    @Column(nullable = false, columnDefinition = "TEXT")
    @Valid
    @Length(max = 256)
    private String text;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_name", updatable = false)
    private User creator;

    @Column(nullable = false, columnDefinition = "VARCHAR(255)", name = "sub_text")
    @Valid
    @Length(max = 140)
    private String subText; //any additional text added after the name, like occasion or date

    @Column(nullable = false, name = "vote_count")
    private int voteCount;

    protected Quote() { }

    public Quote(Person person) {
        this(0, person);
    }

    public Quote(int id, Person person) {
        super(id);
        this.person = person;
    }

    public Person getPerson() {
        return person;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public String getSubText() {
        return subText;
    }

    public void setSubText(String subText) {
        this.subText = subText;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    @Override
    public String toString() {
        return "Quote{" +
                "person=" + person +
                ", text='" + text + '\'' +
                ", creator=" + creator +
                ", subText='" + subText + '\'' +
                ", voteCount=" + voteCount +
                '}';
    }
}
