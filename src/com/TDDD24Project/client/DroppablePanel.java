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

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

import gwtquery.plugins.droppable.client.gwt.DroppableWidget;

/**
 * Droppable Panel containing the portlets.
 * 
 * @author Julien Dramaix (julien.dramaix@gmail.com)
 *
 */
public class DroppablePanel extends DroppableWidget<FlowPanel> {

  private FlowPanel innerPanel;
  protected int column;

  public DroppablePanel(int column) {
	  this.column=column;
    init();
    initWidget(innerPanel);
    setupDrop();
  }

  public int getColumn(){
	  return this.column;
  }
  public void add(Widget p) {
    innerPanel.add(p);
    
  }
  public Widget getWidget(int index){
	  return innerPanel.getWidget(index);
  }
  public void setWidget(Widget widget, int index){
	 
	  innerPanel.remove(index);
	  innerPanel.insert(widget, index);
  }
  private void init() {
    innerPanel = new FlowPanel();
    innerPanel.addStyleName(Resources.INSTANCE.css().sortablePanel());

  }

  /**
   * Register drop handler !
   */
  private void setupDrop() {
    DragAndDropHandler sortableHandler = new DragAndDropHandler(innerPanel);
    addDropHandler(sortableHandler);
    addOutDroppableHandler(sortableHandler);
    addOverDroppableHandler(sortableHandler);
  }

}