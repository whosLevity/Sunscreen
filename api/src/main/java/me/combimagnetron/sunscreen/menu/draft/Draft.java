package me.combimagnetron.sunscreen.menu.draft;

import me.combimagnetron.sunscreen.menu.editor.Editable;
import me.combimagnetron.sunscreen.element.div.Edit;
import me.combimagnetron.sunscreen.element.div.ScrollableDiv;
import me.combimagnetron.sunscreen.style.Style;
import me.combimagnetron.sunscreen.util.Identifier;
import me.combimagnetron.sunscreen.element.Element;
import me.combimagnetron.sunscreen.menu.Position;
import me.combimagnetron.sunscreen.element.div.Div;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

public interface Draft {
    Draft element(Element element, Position position);

    Draft div(Div div, Position position);

    Draft edit(Edit<?> edit);

    final class ElementSubSection implements SubSection<Element> {
        private final List<Function<Element, Element>> element = new ArrayList<>();
        private final Edit.EditBuilder<Element, ElementSubSection> editBuilder;

        public ElementSubSection(Edit.EditBuilder<Element, ElementSubSection> element) {
            this.editBuilder = element;
        }

        public ElementSubSection position(Position pos) {
            element.add(d -> d.position(pos));
            return this;
        }

        public <T> ElementSubSection edit(Class<T> type, Function<T, Element> edit) {
            element.add((Function<Element, Element>) edit);
            return this;
        }

        public <T> ElementSubSection style(Style<T> style, Position position, T t) {
            element.add(d -> d.style(style, position, t));
            return this;
        }

        public <T> ElementSubSection style(Style<T> style, T t) {
            element.add(d -> d.style(style, t));
            return this;
        }

        @Override
        public List<Function<Element, Element>> product() {
            return element;
        }

        @Override
        public Edit.EditBuilder<Element, ElementSubSection> back() {
            return editBuilder;
        }

    }

    final class ScrollDivSubSection extends DivSubSection {

        public ScrollDivSubSection(Edit.EditBuilder<Div, DivSubSection> builder) {
            super(builder);
        }

        public ScrollDivSubSection scroll(double percentage) {
            div.add(d -> ((ScrollableDiv)d).scroll(percentage));
            return this;
        }

        public ScrollDivSubSection scroll(int pixel) {
            div.add(d -> ((ScrollableDiv)d).scroll(pixel));
            return this;
        }

    }

    class DivSubSection implements SubSection<Div> {
        protected final List<Function<Div, Div>> div = new ArrayList<>();
        private final Edit.EditBuilder<Div, DivSubSection> editBuilder;

        public DivSubSection(Edit.EditBuilder<Div, DivSubSection> builder) {
            this.editBuilder = builder;
        }

        public DivSubSection position(Position pos) {
            div.add(d -> d.position(pos));
            return this;
        }

        public DivSubSection add(Element element) {
            div.add(d -> d.add(element));
            return this;
        }

        public DivSubSection remove(Identifier identifier) {
            div.add(d -> d.remove(identifier));
            return this;
        }

        public DivSubSection remove(Element element) {
            div.add(d -> d.remove(element));
            return this;
        }

        public DivSubSection hide(Identifier identifier) {
            div.add(d -> d.hide(identifier));
            return this;
        }

        public DivSubSection hide(Element element) {
            div.add(d -> d.hide(element));
            return this;
        }

        public DivSubSection show(Identifier identifier) {
            div.add(d -> d.show(identifier));
            return this;
        }

        public DivSubSection show(Element element) {
            div.add(d -> d.show(element));
            return this;
        }

        @Override
        public List<Function<Div, Div>> product() {
            return div;
        }

        @Override
        public Edit.EditBuilder<Div, DivSubSection> back() {
            return editBuilder;
        }


    }

    static Draft draft() {
        return new Impl();
    }

    class Impl implements Draft {
        private final Set<Edit<?>> edits = new HashSet<>();

        @Override
        public Draft element(Element element, Position position) {
            return this;
        }

        @Override
        public Draft div(Div div, Position position) {
            return this;
        }

        public Set<Edit<?>> edits() {
            return edits;
        }

        @Override
        public Draft edit(Edit<?> edit) {
            edits.add(edit);
            return this;
        }
    }

    interface SubSection<T extends Editable> {

        List<Function<T, T>> product();

        <V extends SubSection<T>> Edit.EditBuilder<T, V> back();

    }

}
