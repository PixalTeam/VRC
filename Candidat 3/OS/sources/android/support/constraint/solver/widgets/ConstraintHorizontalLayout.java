package android.support.constraint.solver.widgets;

import android.support.constraint.solver.LinearSystem;
import android.support.constraint.solver.widgets.ConstraintAnchor.Strength;
import android.support.constraint.solver.widgets.ConstraintAnchor.Type;

public class ConstraintHorizontalLayout extends ConstraintWidgetContainer {
    private ContentAlignment mAlignment = ContentAlignment.MIDDLE;

    public enum ContentAlignment {
        BEGIN,
        MIDDLE,
        END,
        TOP,
        VERTICAL_MIDDLE,
        BOTTOM,
        LEFT,
        RIGHT
    }

    public ConstraintHorizontalLayout() {
    }

    public ConstraintHorizontalLayout(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public ConstraintHorizontalLayout(int width, int height) {
        super(width, height);
    }

    /* JADX WARNING: type inference failed for: r2v2 */
    /* JADX WARNING: Multi-variable type inference failed */
    public void addToSolver(LinearSystem system) {
        if (this.mChildren.size() != 0) {
            ConstraintHorizontalLayout constraintHorizontalLayout = this;
            int mChildrenSize = this.mChildren.size();
            for (int i = 0; i < mChildrenSize; i++) {
                ConstraintWidget widget = (ConstraintWidget) this.mChildren.get(i);
                if (constraintHorizontalLayout != this) {
                    widget.connect(Type.LEFT, (ConstraintWidget) constraintHorizontalLayout, Type.RIGHT);
                    constraintHorizontalLayout.connect(Type.RIGHT, widget, Type.LEFT);
                } else {
                    Strength strength = Strength.STRONG;
                    if (this.mAlignment == ContentAlignment.END) {
                        strength = Strength.WEAK;
                    }
                    widget.connect(Type.LEFT, (ConstraintWidget) constraintHorizontalLayout, Type.LEFT, 0, strength);
                }
                widget.connect(Type.TOP, (ConstraintWidget) this, Type.TOP);
                widget.connect(Type.BOTTOM, (ConstraintWidget) this, Type.BOTTOM);
                constraintHorizontalLayout = widget;
            }
            if (constraintHorizontalLayout != this) {
                Strength strength2 = Strength.STRONG;
                if (this.mAlignment == ContentAlignment.BEGIN) {
                    strength2 = Strength.WEAK;
                }
                constraintHorizontalLayout.connect(Type.RIGHT, (ConstraintWidget) this, Type.RIGHT, 0, strength2);
            }
        }
        super.addToSolver(system);
    }
}
