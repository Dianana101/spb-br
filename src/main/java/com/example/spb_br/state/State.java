package com.example.spb_br.state;

public interface State<T> {

    void performAction(T context);

}
