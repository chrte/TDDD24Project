/*
 * Copyright 2010 The gwtquery plugins team.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.TDDD24Project.client;

import gwtquery.plugins.draggable.client.events.DragEvent;
import gwtquery.plugins.draggable.client.events.DragEvent.DragEventHandler;
import gwtquery.plugins.draggable.client.gwt.DraggableWidget;
import gwtquery.plugins.droppable.client.events.DropEvent;
import gwtquery.plugins.droppable.client.events.DropEvent.DropEventHandler;
import gwtquery.plugins.droppable.client.events.OutDroppableEvent;
import gwtquery.plugins.droppable.client.events.OutDroppableEvent.OutDroppableEventHandler;
import gwtquery.plugins.droppable.client.events.OverDroppableEvent;
import gwtquery.plugins.droppable.client.events.OverDroppableEvent.OverDroppableEventHandler;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Inspired by the works of Julien Dramaix (julien.dramaix@gmail.com)
 * 
 * @author Henrik Tosteberg - hento581, Christian Tennstedt - chrte707
 * 
 */
public class DragAndDropHandler implements DropEventHandler,
OverDroppableEventHandler, OutDroppableEventHandler, DragEventHandler {

	private HandlerRegistration dragHandlerRegistration;
	private FlowPanel panel;
	private int currentDropIndex;
	protected ProjectServiceAsync projectSvc = GWT.create(ProjectService.class);

	public DragAndDropHandler(FlowPanel panel) {
		this.panel = panel;
		currentDropIndex = -1;
	}

	/**
	 * When draggable is dragging inside the panel, check if the index has
	 * to move
	 */
	public void onDrag(DragEvent event) {
		final DraggableWidget<?> draggable = event.getDraggableWidget();
		SuperWidget widget = (SuperWidget) draggable; 
		widget.isBeingDragged=true;
		
		maybeMoveDropIndex(event.getHelper());
	}

	/**
	 * On drop, insert the draggable at the drop index, remove handler on
	 * the {@link DragEvent} of this draggable
	 */
	public void onDrop(DropEvent event) {
		final DraggableWidget<?> draggable = event.getDraggableWidget();

		SuperWidget widget =  (SuperWidget) panel.getWidget(currentDropIndex); 
		SuperWidget widget2 = (SuperWidget) draggable; 

		int userId = widget2.userId;
		int widget2Position =widget2.position%10;

		swapWidgetPlaceInDatabase(userId, widget.position, widget2.position);

		int tempPosition = widget.position;
		widget.position =  widget2.position;
		widget2.position = tempPosition;
		FlowPanel panel2 =(FlowPanel) widget2.getParent();
		panel2.insert(widget,widget2Position-1);

		panel.insert(draggable, currentDropIndex);
		
		reset();
		widget2.isBeingDragged=false;

	}
	
	/**
	 * Swaps the two widgets positions in the database
	 * @param userId
	 * @param position1
	 * @param position2
	 */

	private void swapWidgetPlaceInDatabase(int userId, int position1, int position2) {

		AsyncCallback<String> callback = new AsyncCallback<String>() {
			public void onFailure(Throwable caught) {
				System.out.println("failure");				
			}

			@Override
			public void onSuccess(String result) {
				System.out.println("positions swapped");

			}
		};
		projectSvc.swapWidgetPlaceInDatabase(userId, position1, position2, callback); 

	}

	/**
	 * When a draggable is out the panel, remove handler on the {@link DragEvent}
	 * of this draggable
	 */
	public void onOutDroppable(OutDroppableEvent event) {
		reset();
	}

	/**
	 * When a draggable is being over the panel, listen on the {@link DragEvent}
	 * of the draggable
	 */
	public void onOverDroppable(OverDroppableEvent event) {
		DraggableWidget<?> draggable = event.getDraggableWidget();
		getDragIndex(draggable, panel.getWidgetIndex(draggable));	
		dragHandlerRegistration = draggable.addDragHandler(this);
	}

	private void getDragIndex(Widget draggable, int initialPosition) {	

		if (initialPosition != -1) {
			currentDropIndex = initialPosition;
		}

	}



	/**
	 * Return the index before which we should insert the draggable if this one is
	 * dropped now
	 * 
	 * @param draggableHelper
	 * @return
	 */
	private int getBeforeInsertIndex(Element draggableHelper) {
		if (panel.getWidgetCount() == 0) {
			// no widget, the draggable should just be added to the panel
			return -1;
		}

		// Compare absoluteTop of the draggable with absoluteTop of all widgets in the panel
		int draggableAbsoluteTop = draggableHelper.getAbsoluteTop();


		for (int i = 0; i < panel.getWidgetCount(); i++) {
			Widget w = panel.getWidget(i);
			int widgetAbsoluteTop = w.getElement().getAbsoluteTop();
			if (widgetAbsoluteTop+80 > draggableAbsoluteTop && widgetAbsoluteTop-80 < draggableAbsoluteTop ) {
				return i;
			}
		}

		// the draggable should just be added at the end of the panel, should never happen
		return -1;
	}

	/**
	 * Check if we have to move the drop index
	 * 
	 * @param draggableHelper
	 */
	private void maybeMoveDropIndex(Element draggableHelper) {

		int beforeInsertIndex = getBeforeInsertIndex(draggableHelper);

		if (currentDropIndex > 0 && beforeInsertIndex == currentDropIndex) {
			
			return;
		}

		if (beforeInsertIndex >= 0) {
			
			currentDropIndex = beforeInsertIndex;
		} else {
			
			currentDropIndex = panel.getWidgetCount() - 1;
		}
	}

	private void reset() {
		// don't listen drag event on the draggable
		dragHandlerRegistration.removeHandler();
		
		dragHandlerRegistration = null;
		currentDropIndex = -1;
	}

}
