interface  GameObservable {
    void registerObserver(Observer observer);
    void deregisterObserver(Observer observer);
}