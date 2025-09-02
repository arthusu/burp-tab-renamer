package com.burpextension;

import burp.api.montoya.BurpExtension;
import burp.api.montoya.MontoyaApi;
import burp.api.montoya.core.ToolType;
import burp.api.montoya.http.message.requests.HttpRequest;
import burp.api.montoya.ui.contextmenu.ContextMenuEvent;
import burp.api.montoya.ui.contextmenu.ContextMenuItemsProvider;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class RepeaterTabRenamerSimple implements BurpExtension {
    private MontoyaApi api;

    @Override
    public void initialize(MontoyaApi api) {
        this.api = api;
        
        api.extension().setName("Simple Tab Renamer");
        
        // Registrar menú contextual
        api.userInterface().registerContextMenuItemsProvider(new SimpleContextMenu());
        
        api.logging().logToOutput("Simple Tab Renamer loaded!");
        api.logging().logToOutput("🎯 Usage: Right-click on any request in Repeater -> 'Rename Tab'");
        api.logging().logToOutput("✅ Automatically extracts method+path from selected request");
        api.logging().logToOutput("📝 Example: 'POST /plans', 'GET /users', etc.");
    }

    private class SimpleContextMenu implements ContextMenuItemsProvider {
        @Override
        public List<Component> provideMenuItems(ContextMenuEvent event) {
            List<Component> items = new ArrayList<>();
            
            if (event.isFromTool(ToolType.REPEATER)) {
                JMenuItem item = new JMenuItem("Rename Tab");
                item.addActionListener(e -> handleContextRename(event));
                items.add(item);
            }
            
            return items;
        }
    }

    private void handleContextRename(ContextMenuEvent event) {
        api.logging().logToOutput("🎝️ Right-click 'Rename Tab' activated!");
        
        try {
            HttpRequest request = getRequestFromEvent(event);
            if (request != null) {
                String name = request.method() + " " + request.path();
                if (name.length() > 25) {
                    name = name.substring(0, 22) + "...";
                }
                
                api.logging().logToOutput("✅ Extracted from selected request: " + name);
                
                if (renameCurrentTab(name)) {
                    api.logging().logToOutput("✅ Menu rename successful: " + name);
                } else {
                    api.logging().logToOutput("❌ Menu rename failed");
                }
            } else {
                api.logging().logToOutput("❌ Could not get request from menu context");
            }
        } catch (Exception e) {
            api.logging().logToError("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private HttpRequest getRequestFromEvent(ContextMenuEvent event) {
        if (!event.selectedRequestResponses().isEmpty()) {
            api.logging().logToOutput("Got request from selectedRequestResponses");
            return event.selectedRequestResponses().get(0).request();
        }
        
        if (event.messageEditorRequestResponse().isPresent()) {
            api.logging().logToOutput("Got request from messageEditorRequestResponse");
            return event.messageEditorRequestResponse().get().requestResponse().request();
        }
        
        api.logging().logToOutput("No request found in event");
        return null;
    }


    private boolean renameCurrentTab(String newName) {
        try {
            for (Window window : Window.getWindows()) {
                if (window.isVisible()) {
                    List<JTabbedPane> panes = findAllTabbedPanes(window);
                    for (JTabbedPane pane : panes) {
                        if (pane.isShowing() && pane.getSelectedIndex() >= 0) {
                            int index = pane.getSelectedIndex();
                            String currentTitle = pane.getTitleAt(index);
                            
                            if (currentTitle != null && (
                                currentTitle.matches("^\\d+$") ||
                                currentTitle.startsWith("GET ") ||
                                currentTitle.startsWith("POST ") ||
                                currentTitle.startsWith("PUT ") ||
                                currentTitle.startsWith("DELETE ") ||
                                currentTitle.contains("Request")
                            )) {
                                api.logging().logToOutput("Renaming tab from '" + currentTitle + "' to '" + newName + "'");
                                pane.setTitleAt(index, newName);
                                
                                String actualName = pane.getTitleAt(index);
                                if (newName.equals(actualName)) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            api.logging().logToError("Error in renameCurrentTab: " + e.getMessage());
        }
        return false;
    }

    private List<JTabbedPane> findAllTabbedPanes(Container container) {
        List<JTabbedPane> panes = new ArrayList<>();
        
        if (container instanceof JTabbedPane) {
            panes.add((JTabbedPane) container);
        }
        
        for (Component child : container.getComponents()) {
            if (child instanceof Container) {
                panes.addAll(findAllTabbedPanes((Container) child));
            }
        }
        
        return panes;
    }
}
