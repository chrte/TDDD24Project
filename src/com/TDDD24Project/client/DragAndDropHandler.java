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
 * All logic of the drag and drop operation is contained in this class.
 * 
 * @author Julien Dramaix (julien.dramaix@gmail.com)
 * 
 */
public class DragAndDropHandler implements DropEventHandler,
    OverDroppableEventHandler, OutDroppableEventHandler, DragEventHandler {

  private HandlerRegistration dragHandlerRegistration;
  private FlowPanel panel;
//  private SimplePanel placeHolder; //TODO: remove later
  private int placeHolderIndex;
  protected ProjectServiceAsync projectSvc = GWT.create(ProjectService.class);

  public DragAndDropHandler(FlowPanel panel) {
    this.panel = panel;
    placeHolderIndex = -1;
  }

  /**
   * When draggable is dragging inside the panel, check if the place holder has
   * to move
   */
  public void onDrag(DragEvent event) {
    maybeMovePlaceHolder(event.getHelper());
  }

  /**
   * On drop, insert the draggable at the place holder index, remove handler on
   * the {@link DragEvent} of this draggable and remove the visual place holder
   */
  public void onDrop(DropEvent event) {
    final DraggableWidget<?> draggable = event.getDraggableWidget();
   
    SuperWidget widget =  (SuperWidget) panel.getWidget(placeHolderIndex); 
    System.out.println(widget.url);
    System.out.println(widget.position);
    SuperWidget widget2 = (SuperWidget) draggable; 
    System.out.println(widget2.position);
    int userId = widget2.userId;
    
    int widget2Position =widget2.position%10;
    
    swapWidgetPlaceInDatabase(userId, widget.position, widget2.position);
    
    int tempPosition = widget.position;
    widget.position =  widget2.position;
    widget2.position = tempPosition;
    FlowPanel panel2 =(FlowPanel) widget2.getParent();
    panel2.insert(widget,widget2Position-1);
    
    panel.insert(draggable, placeHolderIndex);
    System.out.println("the placerHolderindex is " +placeHolderIndex);
    reset();

  }

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
   * of this draggable and remove the visual place holder
   */
  public void onOutDroppable(OutDroppableEvent event) {
    reset();
  }

  /**
   * When a draggable is being over the panel, listen on the {@link DragEvent}
   * of the draggable and put a visaul place holder.
   */
  public void onOverDroppable(OverDroppableEvent event) {
    DraggableWidget<?> draggable = event.getDraggableWidget();
    // create a place holder
//    createPlaceHolder(draggable, panel.getWidgetIndex(draggable));
    dontCreatePlaceHolder(draggable, panel.getWidgetIndex(draggable));	//TODO: UGLYYYY!!!!
    // listen drag event when draggable is over the droppable
    dragHandlerRegistration = draggable.addDragHandler(this);
  }

  private void dontCreatePlaceHolder(Widget draggable, int initialPosition) {	//TODO: NOT A VERY GOOD NAME ;)
//	  placeHolder = new SimplePanel();
//	    placeHolder.addStyleName(Resources.INSTANCE.css().placeHolder());
//	    placeHolder.setHeight("" + $(draggable).height() + "px");
//	    placeHolder.setWidth("" + $(draggable).width() + "px");

	    if (initialPosition != -1) {
//	      panel.insert(placeHolder, initialPosition);
	      placeHolderIndex = initialPosition;
	    }
	    
}

///**	//TODO: Remove later, when all is functional
//   * Create a visual place holder
//   * 
//   * @param draggable
//   * @param initialPosition
//   */
//  private void createPlaceHolder(Widget draggable, int initialPosition) {
//    placeHolder = new SimplePanel();
//    placeHolder.addStyleName(Resources.INSTANCE.css().placeHolder());
//    placeHolder.setHeight("" + $(draggable).height() + "px");
//    placeHolder.setWidth("" + $(draggable).width() + "px");
//
//    if (initialPosition != -1) {
//      panel.insert(placeHolder, initialPosition);
//      placeHolderIndex = initialPosition;
//    }
//  }

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

    // compare absoluteTop of the draggable with absoluteTop od all widget
    // containing in the panel
    int draggableAbsoluteTop = draggableHelper.getAbsoluteTop();
    

    for (int i = 0; i < panel.getWidgetCount(); i++) {
      Widget w = panel.getWidget(i);
      int widgetAbsoluteTop = w.getElement().getAbsoluteTop();
      if (widgetAbsoluteTop+80 > draggableAbsoluteTop && widgetAbsoluteTop-80 < draggableAbsoluteTop ) {
        return i;
      }
    }

    // the draggable should just be added at the end of the panel
    return -1;
  }

  /**
   * Check if we have to move the place holder
   * 
   * @param draggableHelper
   */
  private void maybeMovePlaceHolder(Element draggableHelper) {
    int beforeInsertIndex = getBeforeInsertIndex(draggableHelper);

    if (placeHolderIndex > 0 && beforeInsertIndex == placeHolderIndex) {
      // placeHolder must not move
      return;
    }

    if (beforeInsertIndex >= 0) {
      // move the place holder and keep its position
//      panel.insert(placeHolder, beforeInsertIndex);
      placeHolderIndex = beforeInsertIndex;
    } else {
      // insert the place holder at the end
//      panel.add(placeHolder);
      placeHolderIndex = panel.getWidgetCount() - 1;
    }
  }

  private void reset() {
    // don't listen drag event on the draggable
    dragHandlerRegistration.removeHandler();
    // remove the place holder
//    placeHolder.removeFromParent();
//    placeHolder = null;
    dragHandlerRegistration = null;
    placeHolderIndex = -1;
  }

}
