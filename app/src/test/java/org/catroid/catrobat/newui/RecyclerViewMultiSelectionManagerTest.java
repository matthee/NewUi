package org.catroid.catrobat.newui;

import org.catroid.catrobat.newui.ui.adapter.RecyclerViewMultiSelectionManager;
import org.junit.Test;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class RecyclerViewMultiSelectionManagerTest {
    private int itemCount = 0;

    @Test
    public void testSetItemAsSelected() {
        RecyclerViewMultiSelectionManager<ListItem> manager = createManager();
        ListItem item = createListItem();

        manager.setSelected(item, true);

        assertTrue(manager.getIsSelected(item));
    }

    @Test
    public void testSetItemAsUnselected() {
        RecyclerViewMultiSelectionManager<ListItem> manager = createManager();
        ListItem item = createListItem();

        manager.setSelected(item, true);

        assertTrue(manager.getIsSelected(item));

        manager.setSelected(item, false);

        assertFalse(manager.getIsSelected(item));

    }

    @Test
    public void testGetSelectedItems() {
        RecyclerViewMultiSelectionManager<ListItem> manager = createManager();
        ListItem item1 = createListItem();
        ListItem item2 = createListItem();
        ListItem item3 = createListItem();

        manager.setSelected(item1, true);
        manager.setSelected(item2, true);
        manager.setSelected(item3, true);

        List<ListItem> selectedItems = manager.getSelectedItems();

        assertTrue(selectedItems.contains(item1));
        assertTrue(selectedItems.contains(item2));
        assertTrue(selectedItems.contains(item3));
    }

    @Test
    public void testGetSelectedItemsAfterUnselecting() {
        RecyclerViewMultiSelectionManager<ListItem> manager = createManager();
        ListItem item1 = createListItem();
        ListItem item2 = createListItem();

        manager.setSelected(item1, true);
        manager.setSelected(item2, true);

        List<ListItem> selectedItems = manager.getSelectedItems();

        assertTrue(selectedItems.contains(item1));
        assertTrue(selectedItems.contains(item2));

        manager.setSelected(item1, false);

        selectedItems = manager.getSelectedItems();

        assertEquals(1, selectedItems.size());

        assertFalse(selectedItems.contains(item1));
        assertTrue(selectedItems.contains(item2));
    }

    @Test
    public void testClearSelection() {
        RecyclerViewMultiSelectionManager<ListItem> manager = createManager();
        ListItem item1 = createListItem();
        ListItem item2 = createListItem();

        manager.setSelected(item1, true);
        manager.setSelected(item2, true);

        manager.clearSelection();

        List<ListItem> selectedItems = manager.getSelectedItems();

        assertEquals(0, selectedItems.size());
    }

    @Test
    public void testIsSelectable() {
        assertTrue(createManager().isSelectable(createListItem()));
    }

    @Test
    public void testToggleSelection() {
        RecyclerViewMultiSelectionManager<ListItem> manager = createManager();
        ListItem item = createListItem();

        assertFalse(manager.getIsSelected(item));

        manager.toggleSelected(item);

        assertTrue(manager.getIsSelected(item));

        manager.toggleSelected(item);

        assertFalse(manager.getIsSelected(item));

    }

    private RecyclerViewMultiSelectionManager createManager() {
        return new RecyclerViewMultiSelectionManager<ListItem>();
    }

    private ListItem createListItem() {
        itemCount++;
        return new ListItem("test item " + itemCount);
    }

    private class ListItem {
        private final String mName;

        public ListItem(String name) {
            mName = name;
        }
    }
}
