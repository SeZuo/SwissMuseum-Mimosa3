package ch.sebastienzurfluh.swissmuseum.cms.client.view.cms.edit.page;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.TextArea;

/**
* A TextArea that stretches vertically to fit its contents
* as the user types. There will always be at least one blank
* row at the bottom. A minimum and/or maximum number of visible
* lines may be specified.
*/
public class StretchyTextArea extends TextArea {

// What GWT calls "visible lines" is the value of the textarea's
// row attribute. If row == 0, the browser renders 2 lines. Except
// Firefox, which renders one more row than other browsers in all
// cases: http://code.google.com/p/google-web-toolkit/issues/detail?id=3916
//
// StretchyTextArea will assume you never want less than THRESHOLD visible
// lines (THRESHOLD+1 in Firefox).
private static final int THRESHOLD =
	Window.Navigator.getUserAgent().toLowerCase().contains("firefox") ? 2 : 3;

// manually derived using 13px monospace font
private final double PX_PER_CHAR = 7.8;

private static final int TEXTBOX_VALUECHANGE_EVENTS = Event.ONKEYUP | Event.ONCHANGE | Event.ONPASTE;

private int minVisibleLines;
private int maxVisibleLines;
private final List<Line> lineArray = new ArrayList<Line>();
private boolean stretchingEnabled = true;

public StretchyTextArea() {
this(0);
}

public StretchyTextArea(int minVisibleLines) {
this(minVisibleLines, 0);
}

public StretchyTextArea(int minVisibleLines, int maxVisibleLines) {
if (minVisibleLines < THRESHOLD)
minVisibleLines = THRESHOLD;
setVisibleLines(this.minVisibleLines = minVisibleLines);

// a maxVisibleLines of THRESHOLD means 'no max'
if (maxVisibleLines < THRESHOLD)
maxVisibleLines = THRESHOLD;
this.maxVisibleLines = maxVisibleLines;

sinkEvents(TEXTBOX_VALUECHANGE_EVENTS);
}

@Override
public void onBrowserEvent(Event evt){
super.onBrowserEvent(evt);
if ((DOM.eventGetType(evt) & TEXTBOX_VALUECHANGE_EVENTS) != 0) {
if (isStretchingEnabled()){
maybeChangeVisibleLines();
}
}
}

@Override
public void setVisibleLines(int lines) {
if (lines < THRESHOLD)
lines = THRESHOLD;
if (lines == getVisibleLines())
return;
super.setVisibleLines(lines);
}

public void setMinVisibleLines(int min){
if(min < THRESHOLD){
min = THRESHOLD;
}
if(min != minVisibleLines){
minVisibleLines = min;
setText(getText());
}
}

public void setMaxVisibleLines(int max){
// a maxVisibleLines of THRESHOLD means 'no max'
if(max < THRESHOLD){
max = THRESHOLD;
}
if(max != maxVisibleLines){
maxVisibleLines = max;
setText(getText());
}
}

@Override
public void setText(String text) {
clear();
if (text != null && text.length() > 0) {
super.setText(text);
if (isStretchingEnabled()) {
maybeChangeVisibleLines();
}
}
}

public void clear() {
super.setText("");
setVisibleLines(minVisibleLines);
lineArray.clear();
}

public void setStretchingEnabled(boolean enabled) {
stretchingEnabled = enabled;
}

public boolean isStretchingEnabled() {
return stretchingEnabled;
}

/**
* Since this method is usually called on each keystroke, its algorithm has been optimized
* to be on the order of the number of chars entered/deleted per stroke: if the user types
* normally, it will execute in roughly O(1) time. It is only when pasting/deleting large
* amounts of text with one keystroke that it executes in O(n) time.
*
* <b>Important:</b> If the textarea's CSS <code>height</code> attribute is set this will
* not work.
*/
private void maybeChangeVisibleLines() {
int charSize = totalChars();
int lineLength = (int) (getOffsetWidth()/PX_PER_CHAR);
int textLength = getText().length();

if (textLength > charSize) {
if (lineArray.isEmpty()){
lineArray.add(new Line(true));
}
for (int i=charSize; i<textLength; i++) {
char c = getText().charAt(i);
if (lineLength == getLastLine().inc() || c == '\n') {
if (canAddNewVisibleLine()){
lineArray.add(new Line(true));
setVisibleLines(getVisibleLines()+1);	
} else {
lineArray.add(new Line(false));
}
}
}
} else if (textLength < charSize) {
int diff = charSize - textLength;
while (true) {
if (lineArray.isEmpty()) {
break;
}
int count = getLastLine().getCount();
if (count >= diff) {
getLastLine().setCount(count-diff);
break;
} else {
if (getLastLine().isVisible()) {
setVisibleLines(getVisibleLines()-1);
}
lineArray.remove(lineArray.size()-1);
diff -= count;
}
}
}
}

private boolean canAddNewVisibleLine() {
if (lineArray.size() < minVisibleLines)
return false;
if (maxVisibleLines == THRESHOLD)
return true;
return lineArray.size() < maxVisibleLines;
}

private int totalChars() {
if (lineArray.isEmpty()){
return 0;
}
int count = 0;
for (Line l : lineArray){
count += l.getCount();
}
return count;
}

private Line getLastLine() {
if (lineArray.isEmpty()) {
return null;
}
return lineArray.get(lineArray.size()-1);
}

/**
* Keeps track of the number of chars per line, and whether
* that line caused a new visible line.
*/
private class Line {
private int count = 0;
private final boolean visible;
public Line(boolean visible) {
this.visible = visible;
}
public int inc() {
return ++count;
}
public void setCount(int count) {
this.count = count;
}
public int getCount(){
return count;
}
public boolean isVisible() {
return visible;
}
}
}