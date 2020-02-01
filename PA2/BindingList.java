//
// BindingList
//
// This class implements a logical binding list (or substitution list).  It
// consists of a simple list of variable/value bindings.  A method is 
// provided for adding bindings, one at a time, and for composing binding
// lists together.  Most importantly, a method is provided for searching
// a binding list for the bound value of a given variable, if any.  This
// search is currently done inefficiently, using a linear search of the list.
// The search is recursive, returning a value for which there is no
// subsequent binding, if such exists.  Lastly, a filtering function that
// extracts only bindings of interest (e.g., those involving variables
// in a query) is provided.
//
// David Noelle -- Tue Apr 10 17:08:45 PDT 2007
//


import java.io.*;
import java.util.*;


public class BindingList {

    public List<Binding> pairs;

    // Default constructor ...
    public BindingList() {
	this.pairs = new ArrayList<Binding>();
    }

    // Copy constructor ...
    public BindingList(BindingList bl) {
	this();
	this.pairs.addAll(bl.pairs);
    }

    // compose -- Add all of the bindings in the given binding list to the
    // end of this binding list.
    public void compose(BindingList bl) {
	pairs.addAll(bl.pairs);
    }

    // boundValue -- Search this binding list for a value corresponding to
    // the given variable. Return only the term associated directly with
    // the variable, rather than seeking an equivalent ground term. Return
    //  null if no such binding is found.
    public Term boundValue(Variable v) {
	for (Binding b : pairs) {
	    if (b.var.equals(v)) {
		Term value = b.val;
		return (value);
	    }
	}
	return (null);
    }

    // groundValue -- Search this binding list for a value corresponding
    // to the given variable. Recursively apply bindings for variables 
    // appearing within the value, perhaps producing a ground term value
    // for the given variable. Return null if no such binding is found.
    public Term groundValue(Variable v) {
	for (Binding b : pairs) {
	    if (b.var.equals(v)) {
		Term value = b.val;
		if (b.val.v != null) {
		    // The value term is another variable ...
		    value = groundValue(b.val.v);
		    if (value == null)
			// This is the best we can do ...
			value = b.val;
		}
		if (b.val.f != null) {
		    // The value term is a function, which could contain 
		    // bound variables ...
		    value = new Term(b.val.f.subst(this));
		}
		return (value);
	    }
	}
	return (null);
    }

    // addBinding -- Add the given binding to the binding list.
    public void addBinding(Binding b) {
	pairs.add(b);
    }

    // addVariableBinding -- Add the given binding to the binding list.
    public void addVariableBinding(Variable var, Term val) {
	Binding b = new Binding(var, val);
	this.addBinding(b);
    }

    // filterBindings -- Return a copy of this binding list that only
    // contains bindings for variables in the given set.
    public BindingList filterBindings(Set<Variable> vars) {
	BindingList newBL = new BindingList();
	for (Binding b : pairs) {
	    if (vars.contains(b.var))
		newBL.addVariableBinding(b.var, groundValue(b.var));
	}
	return (newBL);
    }

    // write -- Write this binding list to the given stream.
    public void write(OutputStream str) {
	PrintWriter out = new PrintWriter(str, true);
	out.printf("{\n");
	for (Binding b : pairs) {
	    b.var.write(str);
	    out.printf(" = ");
	    b.val.write(str);
	    out.printf("\n");
	}
	out.printf("}\n");
    }


}

