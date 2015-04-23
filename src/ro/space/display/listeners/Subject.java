
package ro.space.display.listeners;

public interface Subject {

  public void registerObserver(Observer toRegister);

  public void removeObserver(Observer toRemove);

  public void notifyObservers();
}