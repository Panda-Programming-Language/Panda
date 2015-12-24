package org.panda_lang.panda;

import org.panda_lang.panda.core.Particle;
import org.panda_lang.panda.core.syntax.Block;
import org.panda_lang.panda.core.syntax.Essence;
import org.panda_lang.panda.core.syntax.NamedExecutable;
import org.panda_lang.panda.core.syntax.Parameter;
import org.panda_lang.panda.core.syntax.block.PandaBlock;

import java.util.ArrayList;
import java.util.Collection;

public class PandaScript {

    private String name;
    private String author;
    private String version;
    private Collection<PandaBlock> blocks;

    public PandaScript() {
        this.blocks = new ArrayList<>();
    }

    public PandaScript name(String name) {
        this.name = name;
        return this;
    }

    public PandaScript author(String author) {
        this.author = author;
        return this;
    }

    public PandaScript version(String version) {
        this.version = version;
        return this;
    }

    public PandaScript sections(Collection<PandaBlock> blocks) {
        this.blocks = blocks;
        return this;
    }

    public void addPandaBlock(PandaBlock block) {
        this.blocks.add(block);
    }

    public Essence call(Class<? extends Block> blockType, String name, Parameter... parameters) {
        for (PandaBlock pandaBlock : blocks) {
            for (NamedExecutable executable : pandaBlock.getExecutables()) {
                if (executable.getClass() == blockType && executable.getName().equals(name)) {
                    Particle particle = new Particle();
                    particle.setParameters(parameters);
                    return executable.run(particle);
                }
            }
        }
        return null;
    }

    public void callAll(Class<? extends Block> blockType, String name, Parameter... parameters) {
        for (PandaBlock pandaBlock : blocks) {
            for (NamedExecutable executable : pandaBlock.getExecutables()) {
                if (executable.getClass() == blockType && executable.getName().equals(name)) {
                    Particle particle = new Particle();
                    particle.setParameters(parameters);
                    executable.run(particle);
                }
            }
        }
    }

    public Collection<PandaBlock> getBlocks() {
        return blocks;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public String getVersion() {
        return version;
    }

}
