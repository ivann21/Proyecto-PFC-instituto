package calendar.calendar.utils;

import java.awt.event.MouseEvent;
import calendar.calendar.model.ModelDate;

/**
 *
 * @author Raven
 */
public interface CalendarSelectedListener {

    public void selected(MouseEvent evt, ModelDate date);
}
