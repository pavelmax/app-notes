package model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;


/**
 * Свойства обычные, не SimpleStringProperty и т.д, т.к. не требуется по ТЗ синхронизация, редактирование через ячейку и т.д.
 */
@Entity
public class Note extends AbstractEntity {
    @Column(name = "text", nullable = false, length = 100)
    protected String text;

    @Column(name = "create_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    protected Date createAt;


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Note)) return false;

        Note note = (Note) o;

        if (text != null ? !text.equals(note.text) : note.text != null) return false;
        return createAt != null ? createAt.equals(note.createAt) : note.createAt == null;
    }

    @Override
    public int hashCode() {
        int result = text != null ? text.hashCode() : 0;
        result = 31 * result + (createAt != null ? createAt.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Note{");
        sb.append("text='").append(text).append('\'');
        sb.append(", createAt=").append(createAt);
        sb.append(", id=").append(id);
        sb.append('}');
        return sb.toString();
    }
}

