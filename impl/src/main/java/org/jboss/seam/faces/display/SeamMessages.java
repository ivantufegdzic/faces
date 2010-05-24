/*
 * JBoss, Home of Professional Open Source
 * Copyright 2010, Red Hat, Inc., and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.jboss.seam.faces.display;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.event.PhaseEvent;
import javax.inject.Inject;

import org.jboss.seam.faces.event.qualifier.Before;
import org.jboss.seam.faces.event.qualifier.RenderResponse;
import org.jboss.seam.international.status.Level;
import org.jboss.seam.international.status.Message;
import org.jboss.seam.international.status.Messages;

/**
 * Convert Seam Messages into FacesMessages <br>
 * TODO perform EL evaluation.
 * 
 * @author <a href="mailto:lincolnbaxter@gmail.com>Lincoln Baxter, III</a>
 * 
 */
@SessionScoped
public class SeamMessages implements Serializable
{
   private static final long serialVersionUID = -2908193057765795662L;

   @Inject
   Messages sm;

   @SuppressWarnings("unused")
   private void convert(@Observes @Before @RenderResponse final PhaseEvent event)
   {
      for (Message m : sm.getAll())
      {
         event.getFacesContext().addMessage(m.getTargets(), new FacesMessage(getSeverity(m.getLevel()), m.getText(), null));
      }
      sm.clear();
   }

   private Severity getSeverity(final Level level)
   {
      Severity result = FacesMessage.SEVERITY_INFO;
      switch (level)
      {
      case INFO:
         break;
      case WARN:
         result = FacesMessage.SEVERITY_WARN;
         break;
      case ERROR:
         result = FacesMessage.SEVERITY_ERROR;
         break;
      case FATAL:
         result = FacesMessage.SEVERITY_FATAL;
         break;
      default:
         break;
      }
      return result;
   }

}
