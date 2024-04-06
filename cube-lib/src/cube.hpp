#pragma once

#include "puzzle.hpp"

class Cube : public Puzzle<3> {
public:
    explicit Cube(int size);
    void reset();

protected:
    virtual void fill_cubies() override;
    virtual void fill_moves() override;

private:
    int m_size;
};
