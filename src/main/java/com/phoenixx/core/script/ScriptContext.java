package com.phoenixx.core.script;

/**
 * @author Junaid Talpur
 * @project Hydra
 * @since 1:44 PM [29-05-2023]
 */
public class ScriptContext {
    private IScript script;
    private Action action;
    private Transaction transaction;
    private Step step;

    public ScriptContext(IScript script, Action action, Transaction transaction, Step step) {
        this.script = script;
        this.action = action;
        this.transaction = transaction;
        this.step = step;
    }

    public ScriptContext setScript(IScript script) {
        this.script = script;
        return this;
    }

    public ScriptContext setAction(Action action) {
        this.action = action;
        return this;
    }

    public ScriptContext setTransaction(Transaction transaction) {
        this.transaction = transaction;
        return this;
    }

    public ScriptContext setStep(Step step) {
        this.step = step;
        return this;
    }

    public IScript getScript() {
        return this.script;
    }

    public Action getAction() {
        return this.action;
    }

    public Transaction getTransaction() {
        return this.transaction;
    }

    public Step getStep() {
        return this.step;
    }
}
