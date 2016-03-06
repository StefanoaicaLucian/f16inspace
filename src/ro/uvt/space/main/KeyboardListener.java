package ro.uvt.space.main;

import static java.awt.event.KeyEvent.VK_1;
import static java.awt.event.KeyEvent.VK_2;
import static java.awt.event.KeyEvent.VK_3;
import static java.awt.event.KeyEvent.VK_4;
import static java.awt.event.KeyEvent.VK_5;
import static java.awt.event.KeyEvent.VK_6;
import static java.awt.event.KeyEvent.VK_7;
import static java.awt.event.KeyEvent.VK_DOWN;
import static java.awt.event.KeyEvent.VK_LEFT;
import static java.awt.event.KeyEvent.VK_RIGHT;
import static java.awt.event.KeyEvent.VK_UP;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ro.uvt.api.util.Vertex;

public class KeyboardListener implements KeyListener, Subject {

    private Vertex cameraPosition = new Vertex(0.0f, 1.0f, 0.0f);
    private Vertex targetPosition = new Vertex(0.0f, 1.0f, -1.0f);
    private float cameraAngle;

    private float angleStep = 0.1f;
    private float fraction = 0.1f;

    private List<Observer> observers = new ArrayList<>();

    private Renderer associatedRenderer;

    @Override
    public void keyPressed(KeyEvent e) {
	switch (e.getKeyCode()) {
	case VK_LEFT:
	    cameraAngle += angleStep;
	    System.out.println("cameraAngle: " + cameraAngle + " / sin: "
		    + Math.sin(cameraAngle) + " / cos: "
		    + Math.cos(cameraAngle));
	    targetPosition.setPositionX((float) Math.cos(cameraAngle + Math.PI
		    / 2));
	    targetPosition.setPositionZ(-1.0f
		    * (float) Math.sin(cameraAngle + Math.PI / 2));
	    notifyObservers();
	    break;

	case VK_RIGHT:
	    cameraAngle -= angleStep;
	    System.out.println("cameraAngle: " + cameraAngle + " / sin: "
		    + Math.sin(cameraAngle) + " / cos: "
		    + Math.cos(cameraAngle));
	    targetPosition.setPositionX((float) Math.cos(cameraAngle + Math.PI
		    / 2));
	    targetPosition.setPositionZ(-1.0f
		    * (float) Math.sin(cameraAngle + Math.PI / 2));
	    notifyObservers();
	    break;

	case VK_UP:
	    cameraPosition.setPositionX(cameraPosition.getPositionX()
		    + (float) (targetPosition.getPositionX() * fraction));
	    cameraPosition.setPositionZ(cameraPosition.getPositionZ()
		    + (float) (targetPosition.getPositionZ() * fraction));
	    notifyObservers();
	    break;

	case VK_DOWN:
	    cameraPosition.setPositionX(cameraPosition.getPositionX()
		    - (float) (targetPosition.getPositionX() * fraction));
	    cameraPosition.setPositionZ(cameraPosition.getPositionZ()
		    - (float) (targetPosition.getPositionZ() * fraction));
	    notifyObservers();
	    break;

	case VK_1:
	    associatedRenderer.changeParticleEffect(1);
	    notifyObservers();
	    break;

	case VK_2:
	    associatedRenderer.changeParticleEffect(2);
	    notifyObservers();
	    break;

	case VK_3:
	    associatedRenderer.changeParticleEffect(3);
	    notifyObservers();
	    break;

	case VK_4:
	    associatedRenderer.changeParticleEffect(4);
	    notifyObservers();
	    break;

	case VK_5:
	    associatedRenderer.changeParticleEffect(5);
	    notifyObservers();
	    break;

	case VK_6:
	    associatedRenderer.changeParticleEffect(6);
	    notifyObservers();
	    break;

	case VK_7:
	    associatedRenderer.changeParticleEffect(7);
	    notifyObservers();
	    break;

	}
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void registerObserver(Observer toRegister) {
	observers.add(toRegister);
    }

    @Override
    public void removeObserver(Observer toRemove) {
	observers.remove(toRemove);
    }

    @Override
    public void notifyObservers() {
	for (Observer observer : observers) {
	    observer.update(this);
	}
    }

    @Override
    public HashMap<String, Object> getState() {
	HashMap<String, Object> state = new HashMap<>();

	state.put("camera_position", cameraPosition);
	state.put("target_position", targetPosition);
	state.put("camera_angle", cameraAngle);

	return state;
    }

    public void registerRenderer(Renderer aRenderer) {
	associatedRenderer = aRenderer;
    }
}
