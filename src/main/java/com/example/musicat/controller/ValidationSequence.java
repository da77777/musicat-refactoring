package com.example.musicat.controller;

import javax.validation.GroupSequence;
import javax.validation.groups.Default;

@GroupSequence({Default.class, ValidationGroups.NotBlankGroup.class, ValidationGroups.SizeCheckGroup.class})
public interface ValidationSequence {
}
