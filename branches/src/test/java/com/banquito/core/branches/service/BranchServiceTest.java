package com.banquito.core.branches.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.banquito.core.branches.exception.CRUDException;
import com.banquito.core.branches.model.Branch;
import com.banquito.core.branches.repository.BranchRepository;

public class BranchServiceTest {

    @Mock
    private BranchRepository branchRepository;

    @InjectMocks
    private BranchService branchService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLookById() throws CRUDException {
        Branch branch = new Branch();
        branch.setId("1");
        branch.setCode("ABC");
        branch.setName("Sucursal1");

        when(branchRepository.findById("1")).thenReturn(Optional.of(branch));

        Branch foundBranch = branchService.lookById("1");

        assertEquals("ABC", foundBranch.getCode());
    }

    @Test
    public void testLookByCode() {
        Branch branch = new Branch();
        branch.setId("1");
        branch.setCode("ABC");
        branch.setName("Sucursal1");

        when(branchRepository.findByCode("ABC")).thenReturn(branch);

        Branch foundBranch = branchService.lookByCode("ABC");

        assertEquals("1", foundBranch.getId());
        assertEquals("Sucursal1", foundBranch.getName());
    }

    @Test
    public void testGetAll() {
        List<Branch> branches = new ArrayList<>();
        branches.add(new Branch());
        branches.add(new Branch());

        when(branchRepository.findAll()).thenReturn(branches);

        List<Branch> foundBranches = branchService.getAll();

        assertEquals(2, foundBranches.size());
    }

    @Test
    public void testCreate() throws CRUDException {
        Branch branch = new Branch();
        branch.setCode("ABC");
        branch.setName("Sucursal1");

        branchService.create(branch);

        verify(branchRepository, times(1)).save(branch);
    }

    @Test
    public void testUpdate() throws CRUDException {
        Branch existingBranch = new Branch();
        existingBranch.setId("1");
        existingBranch.setCode("ABC");
        existingBranch.setName("Sucursal1");

        Branch updatedBranch = new Branch();
        updatedBranch.setCode("ABC");
        updatedBranch.setName("SucursalNUEVA");

        when(branchRepository.findByCode("ABC")).thenReturn(existingBranch);

        branchService.update("ABC", updatedBranch);

        assertEquals("SucursalNUEVA", existingBranch.getName());
        verify(branchRepository, times(1)).save(existingBranch);
    }
}
